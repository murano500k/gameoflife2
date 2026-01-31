package com.stc.gameoflife2.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.stc.gameoflife2.model.GameConfig

@Composable
fun ControlPanel(
    isRunning: Boolean,
    speedMs: Long,
    onPlayPause: () -> Unit,
    onStep: () -> Unit,
    onClear: () -> Unit,
    onRandomize: () -> Unit,
    onSpeedChange: (Long) -> Unit,
    onGridSizeClick: () -> Unit,
    onAddPatternClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Main controls row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Play/Pause - Primary action with glow
                    PlayPauseButton(
                        isRunning = isRunning,
                        onClick = onPlayPause
                    )

                    // Secondary actions
                    ControlIconButton(
                        icon = Icons.Default.SkipNext,
                        contentDescription = "Step",
                        onClick = onStep,
                        enabled = !isRunning
                    )

                    ControlIconButton(
                        icon = Icons.Default.Delete,
                        contentDescription = "Clear",
                        onClick = onClear
                    )

                    ControlIconButton(
                        icon = Icons.Default.Shuffle,
                        contentDescription = "Randomize",
                        onClick = onRandomize
                    )

                    ControlIconButton(
                        icon = Icons.Default.Add,
                        contentDescription = "Add Pattern",
                        onClick = onAddPatternClick
                    )

                    ControlIconButton(
                        icon = Icons.Default.GridOn,
                        contentDescription = "Grid Size",
                        onClick = onGridSizeClick
                    )
                }

                // Speed slider with labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "SPEED",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Slider(
                        value = (GameConfig.MAX_SPEED_MS - speedMs).toFloat(),
                        onValueChange = { value ->
                            onSpeedChange(GameConfig.MAX_SPEED_MS - value.toLong())
                        },
                        valueRange = 0f..(GameConfig.MAX_SPEED_MS - GameConfig.MIN_SPEED_MS).toFloat(),
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "${speedMs}ms",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .width(50.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayPauseButton(
    isRunning: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isRunning) 1.1f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "playPauseScale"
    )

    val containerColor by animateColorAsState(
        targetValue = if (isRunning)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.primary,
        label = "playPauseColor"
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        // Glow effect when running
        if (isRunning) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.error.copy(alpha = 0.4f),
                                MaterialTheme.colorScheme.error.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }

        FilledIconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .scale(scale),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = containerColor
            )
        ) {
            Icon(
                imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isRunning) "Pause" else "Play",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun ControlIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .size(44.dp)
            .border(
                width = 1.dp,
                color = if (enabled)
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                else
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (enabled)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}
