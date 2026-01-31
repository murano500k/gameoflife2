package com.stc.gameoflife2.engine

import com.stc.gameoflife2.model.Grid
import com.stc.gameoflife2.model.GameRules

/**
 * Interface for game logic computation.
 */
interface GameEngine {
    /**
     * Compute the next generation of the grid.
     */
    fun nextGeneration(grid: Grid, rules: GameRules, wrapEdges: Boolean): Grid

    /**
     * Count alive neighbors for a specific cell.
     */
    fun countNeighbors(grid: Grid, row: Int, col: Int, wrapEdges: Boolean): Int
}