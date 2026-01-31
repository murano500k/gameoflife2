package com.stc.gameoflife2.model

/**
 * Predefined pattern that can be placed on the grid.
 */
data class Pattern(
    val name: String,
    val category: PatternCategory,
    val cells: Set<Cell>,
    val description: String,
    val facts: List<String> = emptyList()
) {
    val width: Int get() = (cells.maxOfOrNull { it.col } ?: -1) + 1
    val height: Int get() = (cells.maxOfOrNull { it.row } ?: -1) + 1
    val cellCount: Int get() = cells.size

    companion object {
        // ==================== Still Lifes ====================
        val BLOCK = Pattern(
            name = "Block",
            category = PatternCategory.STILL_LIFE,
            cells = setOf(
                Cell(0, 0), Cell(0, 1),
                Cell(1, 0), Cell(1, 1)
            ),
            description = "The most common still life. A simple 2x2 square that remains stable forever.",
            facts = listOf(
                "Appears naturally more than any other pattern",
                "Often forms as debris from other patterns",
                "First still life discovered (1970)"
            )
        )

        val BEEHIVE = Pattern(
            name = "Beehive",
            category = PatternCategory.STILL_LIFE,
            cells = setOf(
                Cell(0, 1), Cell(0, 2),
                Cell(1, 0), Cell(1, 3),
                Cell(2, 1), Cell(2, 2)
            ),
            description = "A 6-cell still life resembling a beehive. The second most common naturally occurring still life.",
            facts = listOf(
                "Named for its hexagonal shape",
                "Cannot be constructed from 2 blocks",
                "Has exactly 2 dead cells inside"
            )
        )

        val LOAF = Pattern(
            name = "Loaf",
            category = PatternCategory.STILL_LIFE,
            cells = setOf(
                Cell(0, 1), Cell(0, 2),
                Cell(1, 0), Cell(1, 3),
                Cell(2, 1), Cell(2, 3),
                Cell(3, 2)
            ),
            description = "A 7-cell still life that looks like a loaf of bread. Commonly appears as a byproduct of other patterns.",
            facts = listOf(
                "Third most common still life",
                "Has one dead cell trapped inside",
                "Often created by colliding gliders"
            )
        )

        val BOAT = Pattern(
            name = "Boat",
            category = PatternCategory.STILL_LIFE,
            cells = setOf(
                Cell(0, 0), Cell(0, 1),
                Cell(1, 0), Cell(1, 2),
                Cell(2, 1)
            ),
            description = "A 5-cell still life shaped like a small boat. One of the smallest asymmetric still lifes.",
            facts = listOf(
                "Can be extended into a 'long boat'",
                "Part of many larger stable structures",
                "Asymmetric but very stable"
            )
        )

        val TUB = Pattern(
            name = "Tub",
            category = PatternCategory.STILL_LIFE,
            cells = setOf(
                Cell(0, 1),
                Cell(1, 0), Cell(1, 2),
                Cell(2, 1)
            ),
            description = "A 4-cell diamond-shaped still life. The smallest still life that isn't a block.",
            facts = listOf(
                "Has rotational symmetry",
                "Forms naturally but less common than block",
                "Also called 'diamond'"
            )
        )

        // ==================== Oscillators ====================
        val BLINKER = Pattern(
            name = "Blinker",
            category = PatternCategory.OSCILLATOR,
            cells = setOf(
                Cell(0, 0), Cell(0, 1), Cell(0, 2)
            ),
            description = "The smallest and most common oscillator. Alternates between horizontal and vertical orientations with period 2.",
            facts = listOf(
                "Period: 2 generations",
                "First oscillator discovered",
                "Oscillates between 3 horizontal and 3 vertical cells"
            )
        )

        val TOAD = Pattern(
            name = "Toad",
            category = PatternCategory.OSCILLATOR,
            cells = setOf(
                Cell(0, 1), Cell(0, 2), Cell(0, 3),
                Cell(1, 0), Cell(1, 1), Cell(1, 2)
            ),
            description = "A period-2 oscillator that appears to hop back and forth, resembling a toad's movement.",
            facts = listOf(
                "Period: 2 generations",
                "Discovered by Simon Norton in 1970",
                "Changes shape but stays in same location"
            )
        )

        val BEACON = Pattern(
            name = "Beacon",
            category = PatternCategory.OSCILLATOR,
            cells = setOf(
                Cell(0, 0), Cell(0, 1),
                Cell(1, 0),
                Cell(2, 3),
                Cell(3, 2), Cell(3, 3)
            ),
            description = "Two diagonally adjacent blocks that flash on and off. The blinking corner cells make it look like a beacon.",
            facts = listOf(
                "Period: 2 generations",
                "Made of two diagonal blocks",
                "Corner cells appear and disappear"
            )
        )

        val PULSAR = Pattern(
            name = "Pulsar",
            category = PatternCategory.OSCILLATOR,
            cells = setOf(
                Cell(0, 2), Cell(0, 3), Cell(0, 4), Cell(0, 8), Cell(0, 9), Cell(0, 10),
                Cell(2, 0), Cell(2, 5), Cell(2, 7), Cell(2, 12),
                Cell(3, 0), Cell(3, 5), Cell(3, 7), Cell(3, 12),
                Cell(4, 0), Cell(4, 5), Cell(4, 7), Cell(4, 12),
                Cell(5, 2), Cell(5, 3), Cell(5, 4), Cell(5, 8), Cell(5, 9), Cell(5, 10),
                Cell(7, 2), Cell(7, 3), Cell(7, 4), Cell(7, 8), Cell(7, 9), Cell(7, 10),
                Cell(8, 0), Cell(8, 5), Cell(8, 7), Cell(8, 12),
                Cell(9, 0), Cell(9, 5), Cell(9, 7), Cell(9, 12),
                Cell(10, 0), Cell(10, 5), Cell(10, 7), Cell(10, 12),
                Cell(12, 2), Cell(12, 3), Cell(12, 4), Cell(12, 8), Cell(12, 9), Cell(12, 10)
            ),
            description = "A large, beautiful period-3 oscillator with 4-fold symmetry. One of the most recognized patterns in Game of Life.",
            facts = listOf(
                "Period: 3 generations",
                "Has 48 cells at maximum",
                "Discovered by John Conway himself"
            )
        )

        val PENTADECATHLON = Pattern(
            name = "Pentadecathlon",
            category = PatternCategory.OSCILLATOR,
            cells = setOf(
                Cell(0, 1),
                Cell(1, 0), Cell(1, 2),
                Cell(2, 1), Cell(3, 1), Cell(4, 1),
                Cell(5, 1), Cell(6, 1), Cell(7, 1),
                Cell(8, 0), Cell(8, 2),
                Cell(9, 1)
            ),
            description = "A period-15 oscillator, the longest naturally occurring period. Looks like a row of cells that breathes.",
            facts = listOf(
                "Period: 15 generations",
                "Name means 'fifteen' in Greek",
                "One of the first high-period oscillators found"
            )
        )

        // ==================== Spaceships ====================
        val GLIDER = Pattern(
            name = "Glider",
            category = PatternCategory.SPACESHIP,
            cells = setOf(
                Cell(0, 1),
                Cell(1, 2),
                Cell(2, 0), Cell(2, 1), Cell(2, 2)
            ),
            description = "The smallest and most famous spaceship. Travels diagonally across the grid, moving one cell every 4 generations.",
            facts = listOf(
                "Speed: c/4 diagonal",
                "Discovered by Richard Guy in 1970",
                "Symbol of hacker culture",
                "Used to transmit information in Life computers"
            )
        )

        val LWSS = Pattern(
            name = "Lightweight Spaceship",
            category = PatternCategory.SPACESHIP,
            cells = setOf(
                Cell(0, 1), Cell(0, 4),
                Cell(1, 0),
                Cell(2, 0), Cell(2, 4),
                Cell(3, 0), Cell(3, 1), Cell(3, 2), Cell(3, 3)
            ),
            description = "The smallest orthogonal spaceship. Travels horizontally at half the speed of light (c/2).",
            facts = listOf(
                "Speed: c/2 orthogonal",
                "Also called 'LWSS'",
                "Discovered in 1970",
                "Can be used as a signal in logic circuits"
            )
        )

        val MWSS = Pattern(
            name = "Middleweight Spaceship",
            category = PatternCategory.SPACESHIP,
            cells = setOf(
                Cell(0, 2),
                Cell(1, 0), Cell(1, 4),
                Cell(2, 5),
                Cell(3, 0), Cell(3, 5),
                Cell(4, 1), Cell(4, 2), Cell(4, 3), Cell(4, 4), Cell(4, 5)
            ),
            description = "A medium-sized orthogonal spaceship. Larger than LWSS but travels at the same speed.",
            facts = listOf(
                "Speed: c/2 orthogonal",
                "Also called 'MWSS'",
                "11 cells, 5 more than LWSS",
                "Can escort and protect other patterns"
            )
        )

        val HWSS = Pattern(
            name = "Heavyweight Spaceship",
            category = PatternCategory.SPACESHIP,
            cells = setOf(
                Cell(0, 2), Cell(0, 3),
                Cell(1, 0), Cell(1, 5),
                Cell(2, 6),
                Cell(3, 0), Cell(3, 6),
                Cell(4, 1), Cell(4, 2), Cell(4, 3), Cell(4, 4), Cell(4, 5), Cell(4, 6)
            ),
            description = "The largest basic orthogonal spaceship. Any larger would be unstable without support.",
            facts = listOf(
                "Speed: c/2 orthogonal",
                "Also called 'HWSS'",
                "13 cells total",
                "Largest spaceship of this family"
            )
        )

        // ==================== Methuselahs ====================
        val R_PENTOMINO = Pattern(
            name = "R-pentomino",
            category = PatternCategory.METHUSELAH,
            cells = setOf(
                Cell(0, 1), Cell(0, 2),
                Cell(1, 0), Cell(1, 1),
                Cell(2, 1)
            ),
            description = "A tiny 5-cell pattern that takes 1103 generations to stabilize. The first discovered methuselah.",
            facts = listOf(
                "Stabilizes after 1103 generations",
                "Produces 6 gliders",
                "Final population: 116 cells",
                "Named for its R-like shape"
            )
        )

        val DIEHARD = Pattern(
            name = "Diehard",
            category = PatternCategory.METHUSELAH,
            cells = setOf(
                Cell(0, 6),
                Cell(1, 0), Cell(1, 1),
                Cell(2, 1), Cell(2, 5), Cell(2, 6), Cell(2, 7)
            ),
            description = "A 7-cell pattern that completely vanishes after 130 generations, leaving nothing behind.",
            facts = listOf(
                "Vanishes after exactly 130 generations",
                "Discovered by Dean Hickerson",
                "Leaves no ash or debris",
                "Named for its dramatic death"
            )
        )

        val ACORN = Pattern(
            name = "Acorn",
            category = PatternCategory.METHUSELAH,
            cells = setOf(
                Cell(0, 1),
                Cell(1, 3),
                Cell(2, 0), Cell(2, 1), Cell(2, 4), Cell(2, 5), Cell(2, 6)
            ),
            description = "A 7-cell pattern that takes over 5000 generations to stabilize. Grows into a complex ecosystem.",
            facts = listOf(
                "Stabilizes after 5206 generations",
                "Produces 13 gliders",
                "Final population: 633 cells",
                "One of the longest-lived small patterns"
            )
        )

        val PI_HEPTOMINO = Pattern(
            name = "Pi-heptomino",
            category = PatternCategory.METHUSELAH,
            cells = setOf(
                Cell(0, 0), Cell(0, 1), Cell(0, 2),
                Cell(1, 1),
                Cell(2, 0), Cell(2, 1), Cell(2, 2)
            ),
            description = "A 7-cell pattern shaped like the Greek letter Pi. Creates interesting debris before stabilizing.",
            facts = listOf(
                "Stabilizes after 173 generations",
                "Named for π shape",
                "Creates traffic light and blocks"
            )
        )

        // ==================== Guns ====================
        val GOSPER_GLIDER_GUN = Pattern(
            name = "Gosper Glider Gun",
            category = PatternCategory.GUN,
            cells = setOf(
                Cell(4, 0), Cell(4, 1), Cell(5, 0), Cell(5, 1),
                Cell(2, 12), Cell(2, 13),
                Cell(3, 11), Cell(3, 15),
                Cell(4, 10), Cell(4, 16),
                Cell(5, 10), Cell(5, 14), Cell(5, 16), Cell(5, 17),
                Cell(6, 10), Cell(6, 16),
                Cell(7, 11), Cell(7, 15),
                Cell(8, 12), Cell(8, 13),
                Cell(2, 22), Cell(2, 23),
                Cell(3, 21), Cell(3, 25),
                Cell(4, 20), Cell(4, 26),
                Cell(5, 20), Cell(5, 27),
                Cell(6, 20), Cell(6, 26),
                Cell(7, 21), Cell(7, 25),
                Cell(8, 22), Cell(8, 23),
                Cell(2, 34), Cell(2, 35), Cell(3, 34), Cell(3, 35)
            ),
            description = "The first discovered gun pattern. Produces a new glider every 30 generations indefinitely.",
            facts = listOf(
                "Period: 30 generations per glider",
                "Discovered by Bill Gosper in 1970",
                "Won \$50 prize from John Conway",
                "Proved Life patterns can grow forever"
            )
        )

        // ==================== Other ====================
        val INFINITE_GROWTH_1 = Pattern(
            name = "Infinite Growth",
            category = PatternCategory.OTHER,
            cells = setOf(
                Cell(0, 0), Cell(0, 1), Cell(0, 2), Cell(0, 3),
                Cell(0, 4), Cell(0, 5), Cell(0, 6), Cell(0, 7),
                Cell(0, 9), Cell(0, 10), Cell(0, 11), Cell(0, 12), Cell(0, 13),
                Cell(0, 17), Cell(0, 18), Cell(0, 19),
                Cell(0, 26), Cell(0, 27), Cell(0, 28), Cell(0, 29),
                Cell(0, 30), Cell(0, 31), Cell(0, 32),
                Cell(0, 34), Cell(0, 35), Cell(0, 36), Cell(0, 37), Cell(0, 38)
            ),
            description = "A single row of cells that grows infinitely, producing gliders and other patterns forever.",
            facts = listOf(
                "Grows without bound",
                "Just 39 cells in a row",
                "Creates switch engines",
                "Proves unbounded growth is possible"
            )
        )

        val ALL_PATTERNS = listOf(
            // Still lifes
            BLOCK, BEEHIVE, LOAF, BOAT, TUB,
            // Oscillators
            BLINKER, TOAD, BEACON, PULSAR, PENTADECATHLON,
            // Spaceships
            GLIDER, LWSS, MWSS, HWSS,
            // Methuselahs
            R_PENTOMINO, DIEHARD, ACORN, PI_HEPTOMINO,
            // Guns
            GOSPER_GLIDER_GUN,
            // Other
            INFINITE_GROWTH_1
        )

        val CATEGORIES = ALL_PATTERNS.groupBy { it.category }
    }
}

enum class PatternCategory(val displayName: String, val description: String) {
    STILL_LIFE("Still Lifes", "Patterns that never change"),
    OSCILLATOR("Oscillators", "Patterns that repeat after a fixed number of generations"),
    SPACESHIP("Spaceships", "Patterns that move across the grid"),
    METHUSELAH("Methuselahs", "Small patterns that take a long time to stabilize"),
    GUN("Guns", "Patterns that repeatedly produce spaceships"),
    OTHER("Other", "Miscellaneous interesting patterns")
}
