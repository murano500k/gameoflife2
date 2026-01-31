package com.stc.gameoflife2.model

/**
 * Configurable game rules using B/S notation.
 * Conway's Game of Life: B3/S23 (birth with 3 neighbors, survive with 2 or 3)
 */
data class GameRules(
    val birthConditions: Set<Int>,
    val survivalConditions: Set<Int>
) {
    /**
     * Determines if a cell should be alive in the next generation.
     */
    fun shouldBeAlive(isCurrentlyAlive: Boolean, aliveNeighbors: Int): Boolean {
        return if (isCurrentlyAlive) {
            aliveNeighbors in survivalConditions
        } else {
            aliveNeighbors in birthConditions
        }
    }

    /**
     * Returns B/S notation string (e.g., "B3/S23")
     */
    fun toNotation(): String {
        val birth = birthConditions.sorted().joinToString("")
        val survival = survivalConditions.sorted().joinToString("")
        return "B$birth/S$survival"
    }

    companion object {
        val CONWAY = GameRules(
            birthConditions = setOf(3),
            survivalConditions = setOf(2, 3)
        )

        val HIGH_LIFE = GameRules(
            birthConditions = setOf(3, 6),
            survivalConditions = setOf(2, 3)
        )

        val DAY_AND_NIGHT = GameRules(
            birthConditions = setOf(3, 6, 7, 8),
            survivalConditions = setOf(3, 4, 6, 7, 8)
        )

        val SEEDS = GameRules(
            birthConditions = setOf(2),
            survivalConditions = emptySet()
        )

        fun fromNotation(notation: String): GameRules? {
            val regex = Regex("""B(\d*)/S(\d*)""", RegexOption.IGNORE_CASE)
            val match = regex.matchEntire(notation) ?: return null
            val birth = match.groupValues[1].map { it.digitToInt() }.toSet()
            val survival = match.groupValues[2].map { it.digitToInt() }.toSet()
            return GameRules(birth, survival)
        }
    }
}