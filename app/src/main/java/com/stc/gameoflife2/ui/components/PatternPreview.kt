package com.stc.gameoflife2.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.stc.gameoflife2.model.Pattern
import com.stc.gameoflife2.theme.GameTheme
import kotlin.math.max
import kotlin.math.min

@Composable
fun PatternPreview(
    pattern: Pattern,
    modifier: Modifier = Modifier,
    cellColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = GameTheme.gameColors.gridBackground,
    gridColor: Color = GameTheme.gameColors.gridLines
) {
    val glowColor = GameTheme.gameColors.cellGlow

    val padding = 1
    val previewWidth = pattern.width + padding * 2
    val previewHeight = pattern.height + padding * 2
    val aspectRatio = previewWidth.toFloat() / max(previewHeight, 1)

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(max(aspectRatio, 0.5f).coerceAtMost(2f))
            .background(backgroundColor)
    ) {
        val cellSize = min(
            size.width / previewWidth,
            size.height / previewHeight
        )

        val offsetX = (size.width - cellSize * previewWidth) / 2
        val offsetY = (size.height - cellSize * previewHeight) / 2

        // Draw grid lines
        val lineAlpha = if (cellSize >= 8f) 0.5f else 0.3f
        for (col in 0..previewWidth) {
            val x = offsetX + col * cellSize
            drawLine(
                color = gridColor.copy(alpha = lineAlpha),
                start = Offset(x, offsetY),
                end = Offset(x, offsetY + previewHeight * cellSize),
                strokeWidth = 1f
            )
        }
        for (row in 0..previewHeight) {
            val y = offsetY + row * cellSize
            drawLine(
                color = gridColor.copy(alpha = lineAlpha),
                start = Offset(offsetX, y),
                end = Offset(offsetX + previewWidth * cellSize, y),
                strokeWidth = 1f
            )
        }

        // Draw glow layer if cells are large enough
        if (cellSize >= 6f) {
            for (cell in pattern.cells) {
                val x = offsetX + (cell.col + padding) * cellSize
                val y = offsetY + (cell.row + padding) * cellSize
                val glowPadding = cellSize * 0.3f

                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(glowColor, Color.Transparent),
                        center = Offset(x + cellSize / 2, y + cellSize / 2),
                        radius = cellSize
                    ),
                    topLeft = Offset(x - glowPadding, y - glowPadding),
                    size = Size(cellSize + glowPadding * 2, cellSize + glowPadding * 2),
                    cornerRadius = CornerRadius(cellSize * 0.2f)
                )
            }
        }

        // Draw cells
        for (cell in pattern.cells) {
            val x = offsetX + (cell.col + padding) * cellSize
            val y = offsetY + (cell.row + padding) * cellSize
            val cellPadding = if (cellSize >= 6f) 1f else 0.5f
            val cornerRadius = if (cellSize >= 8f) cellSize * 0.15f else 0f

            if (cellSize >= 6f) {
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            cellColor.copy(alpha = 0.9f),
                            cellColor
                        ),
                        startY = y,
                        endY = y + cellSize
                    ),
                    topLeft = Offset(x + cellPadding, y + cellPadding),
                    size = Size(cellSize - cellPadding * 2, cellSize - cellPadding * 2),
                    cornerRadius = CornerRadius(cornerRadius)
                )
            } else {
                drawRect(
                    color = cellColor,
                    topLeft = Offset(x + cellPadding, y + cellPadding),
                    size = Size(cellSize - cellPadding * 2, cellSize - cellPadding * 2)
                )
            }
        }
    }
}
