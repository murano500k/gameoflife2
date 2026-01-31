package com.stc.gameoflife2.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stc.gameoflife2.ui.components.ControlPanel
import com.stc.gameoflife2.ui.components.GameCanvas
import com.stc.gameoflife2.ui.components.GridSizeDialog
import com.stc.gameoflife2.viewmodel.GameViewModel

enum class Screen {
    GAME,
    PATTERN_SELECTION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    var showGridSizeDialog by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf(Screen.GAME) }

    when (currentScreen) {
        Screen.GAME -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            if (state.isPlacingPattern) {
                                Column {
                                    Text(
                                        text = "Place Pattern",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = state.selectedPattern?.name ?: "",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            } else {
                                Text(
                                    text = "Game of Life",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        actions = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                StatChip(
                                    label = "GEN",
                                    value = state.generation.toString()
                                )
                                StatChip(
                                    label = "POP",
                                    value = state.population.toString()
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                bottomBar = {
                    ControlPanel(
                        isRunning = state.isRunning,
                        speedMs = state.config.speedMs,
                        onPlayPause = viewModel::togglePlayPause,
                        onStep = viewModel::step,
                        onClear = viewModel::clear,
                        onRandomize = { viewModel.randomize() },
                        onSpeedChange = viewModel::setSpeed,
                        onGridSizeClick = { showGridSizeDialog = true },
                        onAddPatternClick = { currentScreen = Screen.PATTERN_SELECTION }
                    )
                },
                containerColor = MaterialTheme.colorScheme.background,
                modifier = modifier
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                )
                            )
                        )
                ) {
                    GameCanvas(
                        grid = state.grid,
                        selectedPattern = state.selectedPattern,
                        onCellToggle = viewModel::toggleCell,
                        onCellDraw = viewModel::setCell,
                        onPatternPlace = viewModel::placePattern,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Cancel FAB for pattern placement
                    AnimatedVisibility(
                        visible = state.isPlacingPattern,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut(),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        FloatingActionButton(
                            onClick = { viewModel.cancelPatternPlacement() },
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            if (showGridSizeDialog) {
                GridSizeDialog(
                    currentRows = state.config.rows,
                    currentCols = state.config.cols,
                    onDismiss = { showGridSizeDialog = false },
                    onConfirm = { rows, cols ->
                        viewModel.setGridSize(rows, cols)
                        showGridSizeDialog = false
                    }
                )
            }
        }

        Screen.PATTERN_SELECTION -> {
            PatternSelectionScreen(
                onPatternSelected = { pattern ->
                    viewModel.selectPattern(pattern)
                    currentScreen = Screen.GAME
                },
                onBack = { currentScreen = Screen.GAME },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun StatChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
