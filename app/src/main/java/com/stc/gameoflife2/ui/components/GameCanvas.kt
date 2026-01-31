package com.stc.gameoflife2.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import com.stc.gameoflife2.model.Grid
import com.stc.gameoflife2.model.Pattern
import com.stc.gameoflife2.theme.GameTheme
import kotlin.math.min

@Composable
fun GameCanvas(
    grid: Grid,
    selectedPattern: Pattern?,
    onCellToggle: (row: Int, col: Int) -> Unit,
    onCellDraw: (row: Int, col: Int, alive: Boolean) -> Unit,
    onPatternPlace: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val gameColors = GameTheme.gameColors
    val aliveColor = gameColors.cellAlive
    val glowColor = gameColors.cellGlow
    val coreColor = gameColors.cellCore
    val deadColor = gameColors.gridBackground
    val gridLineColor = gameColors.gridLines
    val previewColor = gameColors.cellPreview

    var cellSize by remember { mutableFloatStateOf(0f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    var dragPaintAlive by remember { mutableStateOf(true) }
    var lastDragCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    var previewPosition by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val isPlacingPattern = selectedPattern != null

    Canvas(
        modifier = modifier
            .pointerInput(grid.rows, grid.cols, isPlacingPattern) {
                detectTapGestures { offset ->
                    val cell = offsetToCell(offset, cellSize, offsetX, offsetY, grid.rows, grid.cols)
                    cell?.let { (row, col) ->
                        if (isPlacingPattern) {
                            onPatternPlace(row, col)
                        } else {
                            onCellToggle(row, col)
                        }
                    }
                }
            }
            .pointerInput(grid.rows, grid.cols, isPlacingPattern) {
                if (isPlacingPattern) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val cell = offsetToCell(offset, cellSize, offsetX, offsetY, grid.rows, grid.cols)
                            previewPosition = cell
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            val cell = offsetToCell(change.position, cellSize, offsetX, offsetY, grid.rows, grid.cols)
                            previewPosition = cell
                        },
                        onDragEnd = {
                            previewPosition?.let { (row, col) ->
                                onPatternPlace(row, col)
                            }
                            previewPosition = null
                        },
                        onDragCancel = {
                            previewPosition = null
                        }
                    )
                } else {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val cell = offsetToCell(offset, cellSize, offsetX, offsetY, grid.rows, grid.cols)
                            cell?.let { (row, col) ->
                                dragPaintAlive = !grid.isAlive(row, col)
                                onCellDraw(row, col, dragPaintAlive)
                                lastDragCell = row to col
                            }
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            val cell = offsetToCell(change.position, cellSize, offsetX, offsetY, grid.rows, grid.cols)
                            cell?.let { (row, col) ->
                                if (lastDragCell != (row to col)) {
                                    onCellDraw(row, col, dragPaintAlive)
                                    lastDragCell = row to col
                                }
                            }
                        },
                        onDragEnd = {
                            lastDragCell = null
                        },
                        onDragCancel = {
                            lastDragCell = null
                        }
                    )
                }
            }
    ) {
        val availableWidth = size.width
        val availableHeight = size.height

        cellSize = min(availableWidth / grid.cols, availableHeight / grid.rows)

        val gridWidth = cellSize * grid.cols
        val gridHeight = cellSize * grid.rows
        offsetX = (availableWidth - gridWidth) / 2
        offsetY = (availableHeight - gridHeight) / 2

        // Background
        drawRect(color = deadColor)

        // Draw glow layer first (underneath cells)
        if (cellSize >= 4f) {
            for (cell in grid.aliveCells) {
                val x = offsetX + cell.col * cellSize
                val y = offsetY + cell.row * cellSize
                val glowPadding = cellSize * 0.4f

                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(glowColor, Color.Transparent),
                        center = Offset(x + cellSize / 2, y + cellSize / 2),
                        radius = cellSize * 1.2f
                    ),
                    topLeft = Offset(x - glowPadding, y - glowPadding),
                    size = Size(cellSize + glowPadding * 2, cellSize + glowPadding * 2),
                    cornerRadius = CornerRadius(cellSize * 0.3f),
                    blendMode = BlendMode.Plus
                )
            }
        }

        // Draw alive cells with gradient
        for (cell in grid.aliveCells) {
            val x = offsetX + cell.col * cellSize
            val y = offsetY + cell.row * cellSize
            val padding = if (cellSize >= 6f) 1f else 0.5f
            val cornerRadius = if (cellSize >= 8f) cellSize * 0.15f else 0f

            if (cellSize >= 6f) {
                // Multi-layer cell with gradient for larger cells
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(coreColor, aliveColor),
                        startY = y,
                        endY = y + cellSize
                    ),
                    topLeft = Offset(x + padding, y + padding),
                    size = Size(cellSize - padding * 2, cellSize - padding * 2),
                    cornerRadius = CornerRadius(cornerRadius)
                )
            } else {
                // Simple rectangle for small cells
                drawRect(
                    color = aliveColor,
                    topLeft = Offset(x + padding, y + padding),
                    size = Size(cellSize - padding * 2, cellSize - padding * 2)
                )
            }
        }

        // Draw pattern preview
        if (selectedPattern != null && previewPosition != null) {
            val (previewRow, previewCol) = previewPosition!!
            for (patternCell in selectedPattern.cells) {
                val row = previewRow + patternCell.row
                val col = previewCol + patternCell.col
                if (row in 0 until grid.rows && col in 0 until grid.cols) {
                    val x = offsetX + col * cellSize
                    val y = offsetY + row * cellSize
                    val padding = if (cellSize >= 6f) 1f else 0.5f
                    val cornerRadius = if (cellSize >= 8f) cellSize * 0.15f else 0f

                    drawRoundRect(
                        color = previewColor,
                        topLeft = Offset(x + padding, y + padding),
                        size = Size(cellSize - padding * 2, cellSize - padding * 2),
                        cornerRadius = CornerRadius(cornerRadius)
                    )
                }
            }
        }

        // Draw grid lines
        drawGridLines(
            rows = grid.rows,
            cols = grid.cols,
            cellSize = cellSize,
            offsetX = offsetX,
            offsetY = offsetY,
            lineColor = gridLineColor
        )
    }
}

private fun DrawScope.drawGridLines(
    rows: Int,
    cols: Int,
    cellSize: Float,
    offsetX: Float,
    offsetY: Float,
    lineColor: Color
) {
    // Only draw grid lines if cells are large enough to see them
    if (cellSize < 4f) return

    val strokeWidth = if (cellSize >= 10f) 1f else 0.5f
    val alpha = if (cellSize >= 10f) 1f else 0.5f
    val adjustedColor = lineColor.copy(alpha = lineColor.alpha * alpha)

    for (col in 0..cols) {
        val x = offsetX + col * cellSize
        drawLine(
            color = adjustedColor,
            start = Offset(x, offsetY),
            end = Offset(x, offsetY + rows * cellSize),
            strokeWidth = strokeWidth
        )
    }

    for (row in 0..rows) {
        val y = offsetY + row * cellSize
        drawLine(
            color = adjustedColor,
            start = Offset(offsetX, y),
            end = Offset(offsetX + cols * cellSize, y),
            strokeWidth = strokeWidth
        )
    }
}

private fun offsetToCell(
    offset: Offset,
    cellSize: Float,
    offsetX: Float,
    offsetY: Float,
    rows: Int,
    cols: Int
): Pair<Int, Int>? {
    if (cellSize <= 0) return null

    val col = ((offset.x - offsetX) / cellSize).toInt()
    val row = ((offset.y - offsetY) / cellSize).toInt()

    return if (row in 0 until rows && col in 0 until cols) {
        row to col
    } else {
        null
    }
}
