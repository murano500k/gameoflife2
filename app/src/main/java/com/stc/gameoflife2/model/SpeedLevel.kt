package com.stc.gameoflife2.model

/**
 * Abstract speed levels for the game simulation.
 * Higher ordinal = faster speed (lower delay).
 */
enum class SpeedLevel(val delayMs: Long) {
    SPEED_1(1000L),
    SPEED_2(500L),
    SPEED_3(300L),
    SPEED_4(200L),
    SPEED_5(150L),
    SPEED_6(100L),
    SPEED_7(70L),
    SPEED_8(50L),
    SPEED_9(30L),
    SPEED_10(20L);

    val displayNumber: Int get() = ordinal + 1

    companion object {
        val DEFAULT = SPEED_6

        fun fromOrdinal(ordinal: Int): SpeedLevel =
            entries.getOrElse(ordinal) { DEFAULT }
    }
}