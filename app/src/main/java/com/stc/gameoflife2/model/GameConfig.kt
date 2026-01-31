package com.stc.gameoflife2.model

/**
 * Configuration settings for the game.
 */
data class GameConfig(
    val rows: Int = DEFAULT_ROWS,
    val cols: Int = DEFAULT_COLS,
    val rules: GameRules = GameRules.CONWAY,
    val speedLevel: SpeedLevel = SpeedLevel.DEFAULT,
    val wrapEdges: Boolean = true
) {
    val speedMs: Long get() = speedLevel.delayMs

    companion object {
        const val DEFAULT_ROWS = 50
        const val DEFAULT_COLS = 50
        const val MIN_SIZE = 10
        const val MAX_SIZE = 200
    }
}