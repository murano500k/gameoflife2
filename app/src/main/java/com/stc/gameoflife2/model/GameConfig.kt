package com.stc.gameoflife2.model

/**
 * Configuration settings for the game.
 */
data class GameConfig(
    val rows: Int = DEFAULT_ROWS,
    val cols: Int = DEFAULT_COLS,
    val rules: GameRules = GameRules.CONWAY,
    val speedMs: Long = DEFAULT_SPEED_MS,
    val wrapEdges: Boolean = true
) {
    companion object {
        const val DEFAULT_ROWS = 50
        const val DEFAULT_COLS = 50
        const val MIN_SIZE = 10
        const val MAX_SIZE = 200
        const val DEFAULT_SPEED_MS = 100L
        const val MIN_SPEED_MS = 20L
        const val MAX_SPEED_MS = 1000L
    }
}