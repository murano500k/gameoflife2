package com.stc.gameoflife2.engine

import com.stc.gameoflife2.model.Cell
import com.stc.gameoflife2.model.Grid
import com.stc.gameoflife2.model.GameRules

/**
 * Standard implementation of the game engine.
 * Uses sparse cell representation for efficiency.
 */
class ConwayGameEngine : GameEngine {

    override fun nextGeneration(grid: Grid, rules: GameRules, wrapEdges: Boolean): Grid {
        val newAliveCells = mutableSetOf<Cell>()

        // Collect all cells to evaluate: alive cells and their neighbors
        val cellsToEvaluate = mutableSetOf<Cell>()

        for (cell in grid.aliveCells) {
            cellsToEvaluate.add(cell)
            cellsToEvaluate.addAll(getNeighborCells(cell, grid.rows, grid.cols, wrapEdges))
        }

        // Evaluate each cell
        for (cell in cellsToEvaluate) {
            val isAlive = grid.isAlive(cell)
            val aliveNeighbors = countNeighbors(grid, cell.row, cell.col, wrapEdges)

            if (rules.shouldBeAlive(isAlive, aliveNeighbors)) {
                newAliveCells.add(cell)
            }
        }

        return grid.copy(aliveCells = newAliveCells)
    }

    override fun countNeighbors(grid: Grid, row: Int, col: Int, wrapEdges: Boolean): Int {
        var count = 0
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue

                val neighborRow = if (wrapEdges) {
                    (row + dr + grid.rows) % grid.rows
                } else {
                    row + dr
                }

                val neighborCol = if (wrapEdges) {
                    (col + dc + grid.cols) % grid.cols
                } else {
                    col + dc
                }

                if (!wrapEdges && (neighborRow !in 0 until grid.rows || neighborCol !in 0 until grid.cols)) {
                    continue
                }

                if (grid.isAlive(neighborRow, neighborCol)) {
                    count++
                }
            }
        }
        return count
    }

    private fun getNeighborCells(cell: Cell, rows: Int, cols: Int, wrapEdges: Boolean): List<Cell> {
        val neighbors = mutableListOf<Cell>()
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue

                val neighborRow = if (wrapEdges) {
                    (cell.row + dr + rows) % rows
                } else {
                    cell.row + dr
                }

                val neighborCol = if (wrapEdges) {
                    (cell.col + dc + cols) % cols
                } else {
                    cell.col + dc
                }

                if (wrapEdges || (neighborRow in 0 until rows && neighborCol in 0 until cols)) {
                    neighbors.add(Cell(neighborRow, neighborCol))
                }
            }
        }
        return neighbors
    }
}