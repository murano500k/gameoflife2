package com.stc.gameoflife2.model

/**
 * Complete game state snapshot.
 * Immutable - each state transition creates a new instance.
 */
data class GameState(
    val grid: Grid,
    val config: GameConfig,
    val generation: Long = 0,
    val isRunning: Boolean = false,
    val selectedPattern: Pattern? = null
) {
    val isPlacingPattern: Boolean get() = selectedPattern != null
    val population: Int get() = grid.countAlive()

    companion object {
        fun initial(config: GameConfig = GameConfig()): GameState {
            return GameState(
                grid = Grid.empty(config.rows, config.cols),
                config = config
            )
        }
    }
}