package com.stc.gameoflife2.model

import kotlin.random.Random

/**
 * Immutable grid representation using Set<Cell> for sparse storage.
 * Only alive cells are stored, making large grids with few alive cells efficient.
 */
data class Grid(
    val rows: Int,
    val cols: Int,
    val aliveCells: Set<Cell> = emptySet()
) {
    fun isAlive(row: Int, col: Int): Boolean = Cell(row, col) in aliveCells

    fun isAlive(cell: Cell): Boolean = cell in aliveCells

    fun toggleCell(row: Int, col: Int): Grid {
        val cell = Cell(row, col)
        return if (cell in aliveCells) {
            copy(aliveCells = aliveCells - cell)
        } else {
            copy(aliveCells = aliveCells + cell)
        }
    }

    fun setCell(row: Int, col: Int, alive: Boolean): Grid {
        val cell = Cell(row, col)
        return if (alive) {
            copy(aliveCells = aliveCells + cell)
        } else {
            copy(aliveCells = aliveCells - cell)
        }
    }

    fun clear(): Grid = copy(aliveCells = emptySet())

    fun resize(newRows: Int, newCols: Int): Grid {
        val filteredCells = aliveCells.filter {
            it.row in 0 until newRows && it.col in 0 until newCols
        }.toSet()
        return Grid(newRows, newCols, filteredCells)
    }

    fun countAlive(): Int = aliveCells.size

    fun placePattern(pattern: Pattern, atRow: Int, atCol: Int): Grid {
        val newCells = pattern.cells.mapNotNull { cell ->
            val newRow = atRow + cell.row
            val newCol = atCol + cell.col
            if (newRow in 0 until rows && newCol in 0 until cols) {
                Cell(newRow, newCol)
            } else {
                null
            }
        }.toSet()
        return copy(aliveCells = aliveCells + newCells)
    }

    companion object {
        fun empty(rows: Int, cols: Int) = Grid(rows, cols, emptySet())

        fun randomized(rows: Int, cols: Int, density: Float = 0.3f): Grid {
            val alive = mutableSetOf<Cell>()
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    if (Random.nextFloat() < density) {
                        alive.add(Cell(row, col))
                    }
                }
            }
            return Grid(rows, cols, alive)
        }
    }
}