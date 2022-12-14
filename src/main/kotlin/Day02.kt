import HandShape.Paper.beats
import HandShape.Rock.defeatedBy

private sealed interface HandShape {
    val value: Int
    val defeats: HandShape

    infix fun HandShape.beats(other: HandShape): Boolean = this.defeats == other

    fun HandShape.defeatedBy(): HandShape = this.defeats.defeats

    object Rock : HandShape {
        override val value: Int
            get() = 1
        override val defeats: HandShape
            get() = Scissors
    }

    object Paper : HandShape {
        override val value: Int
            get() = 2
        override val defeats: HandShape
            get() = Rock
    }

    object Scissors : HandShape {
        override val value: Int
            get() = 3
        override val defeats: HandShape
            get() = Paper
    }
}

private fun mapToHandShape(character: Char): HandShape =
    when (character) {
        'A', 'X' -> HandShape.Rock
        'B', 'Y' -> HandShape.Paper
        'C', 'Z' -> HandShape.Scissors
        else -> error("Invalid hand")
    }

private fun Pair<HandShape, HandShape>.defaultStrategyGuide(): Int {
    val (hand1, hand2) = this
    return when {
        hand1 == hand2 -> hand1.value + 3
        hand1 beats hand2 -> hand2.value
        hand2 beats hand1 -> hand2.value + 6
        else -> error("Unexpected combination of hand shape")
    }
}

private fun Pair<HandShape, HandShape>.topSecretStrategyGuide(): Int {
    val (hand1, hand2) = this
    return when (hand2) {
        HandShape.Rock -> hand1.defeats.value
        HandShape.Paper -> hand1.value + 3
        HandShape.Scissors -> hand1.defeatedBy().value + 6
    }
}

private fun playTournament(rounds: String, scoringStrategy: (Pair<HandShape, HandShape>) -> Int) =
    rounds.split("\n")
        .map { Pair(mapToHandShape(it.first()), mapToHandShape(it.last())) }
        .sumOf { scoringStrategy(it) }

fun main() {
    println(playTournament(TEST2_INPUT) { it.defaultStrategyGuide() })
    println(playTournament(PUZZLE2_INPUT) { it.defaultStrategyGuide() })

    println(playTournament(TEST2_INPUT) { it.topSecretStrategyGuide() })
    println(playTournament(PUZZLE2_INPUT) { it.topSecretStrategyGuide() })
}

private const val TEST2_INPUT: String =
    """A Y
B X
C Z"""

private const val PUZZLE2_INPUT: String =
    """C X
C Y
C X
B X
B Z
A Z
C Y
C Z
B Z
C X
B Y
C Y
C Y
A Y
C Y
C Y
C Z
C X
B Z
C Y
A Y
A Y
C Z
B Y
A Y
C Z
C Y
A Y
A Y
B Y
C Y
C Z
C Y
B X
B Z
C Y
B Z
A X
C Z
A Y
B Y
C Y
C Y
B Z
B Y
A Z
C X
C X
C Y
C X
B Z
A Y
B X
B Z
C Z
C X
C X
B Z
A Y
B Y
C Y
C Y
A Y
C X
A Y
B Z
C Y
C Y
B Y
C Y
A Z
A Z
B X
A Y
C Y
A Y
C Y
C Y
C X
C Y
B Z
C Y
C Z
C X
B X
C Y
C Y
C X
C Z
A Y
C X
B Z
C X
A Y
B Y
C Y
A Y
A Y
A Y
B Y
C Y
A Y
A Y
C Z
C Y
B Y
C X
C Y
B Z
B Z
C X
C Y
C X
C Y
C Y
A Y
C Y
C X
C Y
C Y
B Y
B Z
A Y
B Y
A Y
A Y
C Y
B X
C Y
C X
A Y
C X
C X
C Y
C Y
B Z
C X
A Y
B Z
C Z
C X
A Y
B Z
C Y
B Y
A Y
A Y
C Y
B Y
C Y
C X
C Y
A Y
C Y
A Y
B Y
C X
C Y
C Y
B Z
B Z
A Y
C Y
C Y
C Y
C Y
A Y
C X
C Z
C X
A Y
A Y
A Y
C Y
B Z
B Z
C Y
C Y
B X
C Y
C Y
B Z
B X
B Y
C Y
C Y
C X
B Y
B Z
B Z
C Y
C Y
C X
C X
A Y
C Y
C Y
B X
B Z
B X
B Z
B Y
C X
B X
B Y
B Z
B Z
B X
B Z
B Y
C X
C Y
C X
C X
B Z
C Y
C Y
C Y
B Z
C Y
C X
C Y
C Y
A Z
C Y
A Y
C Y
A Z
A X
C Y
C Y
C Y
C Z
B Z
B Z
C Y
B Z
B Z
C X
C Y
A Y
A Y
C Y
A Y
C Y
C Y
B Y
A Y
C Y
B Z
A Y
B Z
C X
C Y
A Y
B Z
A Y
C X
C Y
B Y
C Y
A Z
B Z
B X
C Y
A Y
A Z
C X
A Y
B Z
A Y
B Y
C Y
C Y
B Z
B Z
C X
B Z
C X
C Z
C Y
C X
A Y
C X
A Y
A Y
B Z
B Z
B Z
A Y
A Y
C Y
A Y
B Z
C X
C Y
C Y
C Y
B Y
A Y
C Z
C Y
A Y
A Y
B Z
C Y
C Y
C Y
B Z
C Y
C Y
A Y
A Y
C Y
C X
B Z
B Z
C X
C X
C Z
A Y
C Y
A Y
A Y
C X
A Z
B Y
A Y
C X
A Y
C X
C X
A Y
A Y
C X
B Y
B Z
C Y
C Z
C Y
C Y
B Z
C Y
A Y
A X
B Y
C Y
A Y
C Y
C Y
C Y
A Y
C Y
A Y
C Y
B Z
A Y
C X
C Y
A Y
C Y
A Y
A Y
A Y
B Z
C Y
B Z
A Y
C Y
A Y
C Y
C X
B Z
A Y
C X
B Z
B Y
C Y
C Z
C Y
B Y
B Y
A Y
A Y
C X
B Y
A Y
C Y
B X
C X
A Y
C Y
A Y
C Y
B Z
A Z
B Z
B Z
A Z
C Y
C Y
C X
B Y
C Y
A Y
C Y
C Y
B Y
B Z
C Y
C X
A Y
C X
C X
C Y
B Y
C Y
B Z
C Y
A Y
A Y
C Y
C X
C X
C X
C Y
C X
C Y
C Y
A Y
C Y
C X
C Y
C X
C X
C X
A Y
B Y
C Y
C Y
C Y
C Y
C Y
A Y
A X
B Z
C X
C Y
C Y
B Z
A Y
A Y
B X
B Z
C Y
C Y
B Z
A Y
A Y
A Y
C Y
C Y
A Y
A Y
C Y
C Y
C X
C X
C X
B Z
C Z
C X
A Z
C Y
C X
A Y
B Y
B Z
C X
C Y
C Y
C Z
C Y
C X
A Z
C Y
A X
B Y
C Y
C Y
C Y
C X
B Y
B Y
C X
B X
A Y
C Y
C Y
C Y
B X
C Y
A Y
C Y
C Y
B Z
C X
B X
C Z
C Y
A Y
B Y
B Z
C Z
C X
C X
A Y
A Y
B Z
A Y
C X
B Y
C Y
A Y
B X
A Y
A Y
C Z
C Y
C Z
B Z
A X
C Y
B Y
C Y
B Z
B Y
C Y
A Y
B Z
B Z
C X
B Z
C Y
B Z
B Z
C Y
C Y
B Z
C Z
C Y
B Z
C Y
B X
A Y
B Z
A Y
C Y
A Y
C Y
C Y
B Y
B Z
B Z
C Y
A Y
B Z
C X
C Y
C Y
A Y
C Y
C Y
C X
A Y
C Y
C Y
A Y
C X
A Y
A Y
A Y
C Y
C Y
A Y
B Z
A Y
B Y
A Y
A Y
C Y
C Y
C Z
A Y
C Y
B Z
A Y
C Y
C Z
C Y
C Y
B Z
C Y
C Y
B Z
B Y
C Y
A Y
C X
C Y
C Z
C Z
C X
C X
C X
C Z
C Y
B Z
A Y
B Z
B Z
A Y
C Y
C Y
C Y
B X
C Y
A Y
A Z
B Y
B Y
A Y
B Z
A Y
B Y
C Z
C Y
C Y
C Y
B Z
B Z
B Z
C Y
C Y
A Y
C X
C X
A Y
C Y
C Y
C Y
C Y
C X
C Y
C Y
C Y
C X
A Z
B Y
C Y
A Y
A Y
B Z
A Z
C Y
C Y
C Y
C X
B X
B Y
C Y
A Y
A Z
B X
C Y
C Y
B Z
A Y
A Y
B Z
B Z
C Y
C Y
C X
B Y
B Z
C Y
B Y
C Y
A Y
C X
C X
B Z
A Y
C Y
B Z
C X
C Y
B Y
B Z
A Z
B Y
B Y
C X
C Y
A Z
C X
C X
A Y
A Y
C X
A Y
B Y
B Z
A Y
B Z
B X
C Y
C Y
C X
B Z
A Y
B Y
A Y
C Y
C Z
B Z
C Y
A Y
C Y
C Z
A Z
B Z
B X
C Y
C Y
B Y
C X
B Y
B Z
B Z
B X
B Y
C X
C Y
C X
C Y
A Y
A Y
C Y
A Y
C Y
C X
C Y
A Y
B Z
C X
C Y
B Z
C Y
C Y
B Y
B Z
A X
C Y
B Y
B Y
B Z
C Y
B Z
A Y
C Y
B Y
C Y
A Y
C Y
B Z
C Y
A Y
A Y
C Y
A Y
C Y
A Y
A Y
B Z
C X
C Y
B Z
C Y
A X
A Z
C Y
C X
C X
C Y
C Y
B Z
A Z
A Y
C Y
B Z
A Y
A Y
C Y
B Z
A Y
C X
A Y
A Y
C X
B Z
B X
A X
A Y
B Y
A X
B X
C X
C Y
A Y
C Y
A Y
A Y
C X
B Y
C X
B Y
C Y
C Y
A Y
B Z
A Z
B Y
C X
C Y
C Y
B Z
C Z
A Y
A Y
A Z
C Y
A Y
B Z
C Y
C Y
B Y
A Y
A Y
C Y
C X
C X
B Y
B Z
C Y
B Y
B Z
C Y
A Y
B Z
A Y
A Y
A Z
C X
B Z
C Y
B Z
A Y
C X
C Y
C Y
B X
B Z
A Y
A Y
A Y
A Y
A Y
C Y
A Y
A Y
C X
C Y
C Z
B Z
A Z
C Z
A Y
B Z
B Y
A Y
C Y
B Z
A Y
C Y
C Y
A Y
C Y
B Z
B Y
B Z
C Y
C Y
C Y
A Z
C X
B X
B Z
C Z
C Y
C Y
C Y
C X
A Y
C Y
A Y
A Y
B Z
A Y
C Y
C Y
A Y
C Y
A Z
B X
A Y
C Y
A Y
A Z
A Y
A Y
B Z
B Y
B Z
C Y
B Z
C X
A Y
C Y
A Y
A Y
A Y
B Z
B Z
C Y
B Z
B Y
C X
A Y
C Y
A Y
B Z
C X
B Y
C Y
B Z
C Y
A Y
C Y
C Y
B Y
B Z
C Y
B Z
B Y
C Y
C X
A Z
C Z
C Y
C Y
A Y
A Y
C X
C Y
C Y
C Y
C X
B Z
C X
C Y
A Y
A Y
B Z
A Y
A Y
C Y
A Y
B Z
C X
C Y
C Y
C Y
C Y
C X
B Z
A Y
B Y
C Y
C X
B Z
C Y
A Y
A Y
B Z
C Y
A Y
C Z
C X
B Z
C Y
A Z
B Z
A Y
A Y
C Y
B Z
B Z
A Y
C Y
B Y
C Y
A Y
A Y
B Y
B X
B Z
C Y
A Y
C Y
A Y
A Y
B Z
B Y
A Y
A Y
A Y
C Y
B Y
C Y
A Y
C X
C Y
C Y
B Z
B Z
C Y
C Y
A Y
B X
C X
A Y
C Y
C Y
C Y
A Y
C X
B Z
A Y
C Z
B Y
B Y
C Z
A Y
C Z
C Y
C X
B Y
A Y
C X
C X
C Y
C X
C X
B Z
C Y
B Y
A Y
B Y
A Y
B Z
C X
C Y
C X
C Z
C X
B Z
C X
C Y
C Y
C Y
B Z
A Y
C Y
C Y
C Y
B Z
A Y
C X
C Y
C Y
C Y
C X
A Z
C Y
C Y
C X
B X
B Z
A Y
B Z
C Y
B Y
B Z
A Y
B Z
C Y
A Y
A X
C X
C X
C Y
A Y
B X
C Y
B X
B Z
C X
A Y
B X
C X
A Y
C Y
C Y
C Y
A Z
C Y
A Y
B Z
B Z
A Z
C Y
A Y
B Z
C Z
C X
C X
C X
A Y
A X
C Y
A Y
B Y
C Y
C X
C Y
C Y
A Y
B Z
C Y
B Z
A Y
C X
C Y
B Z
C Y
C Y
B Y
A Y
A Y
A Z
C Y
B Z
B Y
B Z
B Y
A Y
C Y
C Y
C X
C Y
C Y
B Z
C Y
C Y
C X
B Y
B Z
C Y
C Y
A Y
C Z
C Y
B Z
A Y
A X
A Y
C X
C Y
C Y
B Z
A Y
B X
C Z
C Z
C X
C Y
B Z
C Z
B Z
C Y
A Y
C Y
A Y
B X
C X
C Y
B Z
C Y
B Y
B X
B X
B Z
C Y
C Y
B Y
B Z
C Y
A Y
B Z
C Y
B Z
C Y
B Z
C Y
A Y
A Y
C Y
C Y
B Y
B Z
C Y
B Z
C X
B Z
C Y
B X
C X
B Z
A Y
C X
B Z
C Y
A Y
B Z
A X
B Z
B Z
B Z
C Y
C Y
C Y
C Y
A Y
A Y
B Z
B X
C X
C Y
B Y
A Y
A Y
B Z
C Y
A Y
A Y
C Y
C X
C Y
C X
C Y
C Y
C Y
B Z
C Y
C X
B Y
C Y
C Y
C Y
A Z
C X
C Y
A Y
C Y
B Z
B Y
C Y
A Y
A Y
C Y
C Y
C X
A Y
C Y
A Z
A Y
B Z
A Y
C X
C Y
C Y
B X
B Z
C X
B X
C Z
C X
B Z
B Z
C Y
C X
B Z
B Z
B Z
C Y
C Y
A Y
C Y
B Y
B X
C Y
C Y
C Y
B X
C X
B X
C Y
A Y
C Y
C X
C Y
C Y
B X
B Z
A Z
B Z
A Y
B Z
C X
B Z
C Y
C Y
C Z
A Y
C Y
C Y
A Z
B Y
C Y
A X
A Y
B Y
C Y
B Z
C Y
C X
C Y
B Z
A Y
C X
C Y
C X
C Y
C Y
C Z
A Y
C Y
B Z
A Y
C Z
C Y
A Y
C Y
C Y
C Y
C Y
A Y
C Y
C Y
C Y
C Y
B Z
C X
A Y
A Y
C Y
B Y
C Y
C Y
A Z
C Y
B Y
C Y
B Z
C X
A Y
C Y
C Y
B Z
C Y
A Y
A Y
C Y
B Z
C Y
A Y
B Z
C Y
C Y
B Z
C X
B Z
B Z
C Y
C Y
A Y
C X
A Y
C Y
B Z
A Y
B Z
A Y
C X
C Y
B Z
B Y
B Z
A Y
B Z
C Y
B Z
C Y
B Y
A Y
B Z
B X
B Z
C Y
B Z
B Z
B Z
A Y
A Y
C Y
B Z
B Z
C X
B Z
C Z
A Y
B Z
B Z
C Y
B Z
B Z
C X
C Z
A Z
C Y
C Y
B X
C Z
C Y
A Y
B Z
C X
B Z
C Y
C Y
B Z
B Z
B Z
C X
C Y
C X
C X
A Y
B Z
B Y
C Y
C Y
B Z
C Y
C Y
C Y
C Y
C Y
B X
A Y
A Y
B Y
C Y
B Y
C Y
B Y
B Z
C Y
C Y
C X
B Y
A Y
B X
B Z
C X
A X
C Y
A Y
A Y
C X
B Y
A Y
B Z
C X
B Z
C Z
C X
C Y
C X
C Y
C Y
C Y
B Y
C Z
C X
B Z
C Y
C X
C X
B Y
C Y
C Y
C Z
B Z
C Y
C Y
B Y
C Z
C X
B Z
C Y
C X
C Y
B Z
C Y
C Y
A Y
B Y
C X
C Y
C Y
C Y
B Y
B Y
C Z
B Z
C Y
B Z
A Z
C X
B X
C X
A Y
C Y
A Z
C X
C Y
A Y
A Y
B Y
A Y
C Y
B Y
C X
C X
C X
C X
C Y
C Y
A Y
B Z
C Y
A Z
C Y
A Y
B Z
B Z
C Y
A Z
C Y
C Y
C Y
B Z
C Y
C Y
A X
A Y
A Z
C Y
A Y
B X
A Y
B Z
B Y
B Z
B Y
C X
C X
A Y
B Z
B Y
C X
B X
B Y
A Y
C Y
C Y
C Y
B Z
C Y
A Y
B Z
C X
B Z
B Z
B X
B Y
C Y
C Y
C X
B Z
C Y
C Y
C Y
A Y
C Y
B Z
B Y
C Y
C X
C X
B X
B Z
A Z
C X
A Y
B Y
C Y
A Y
B Y
C Y
C Y
C Z
C Y
C X
A Y
C Y
C Y
B Z
C Y
C Y
C Y
C Y
A Y
B Z
B Z
C Y
C Y
C Y
C Y
C Y
B Z
C Y
C Y
C X
A Y
A Y
C Y
C X
A Y
B Z
A Y
C X
C Y
C Z
C Y
A Z
A Y
A Y
B Y
C Y
C Y
B Y
C Y
A Y
C X
B Z
C X
A Y
B Z
C X
A Y
C X
C X
A Z
A Y
B Y
A Y
B Y
A Z
C Y
C Y
C Y
B Z
C Y
A Y
B Z
B Y
C Y
B Y
C Z
A Y
B Y
C Y
B Y
A Y
B Z
C Y
C X
C Y
A Y
A Z
C Y
B Z
C Y
B Y
A Y
C X
A Z
C Z
C X
A Y
C X
C X
C Y
C Z
A Y
A Y
C Y
A Y
C Y
B Z
C X
A X
C X
C Y
C Y
C Y
C Y
B Z
B Z
B Z
C X
C Y
B Y
C Y
B Y
C Z
A X
B Y
C X
B Y
C X
B Y
C Y
B Z
C X
A Y
B Z
C X
A X
C Y
B Z
B Z
C X
B Z
B Z
C X
C Y
C Z
B Z
C Y
C Y
C Y
B X
A Y
B Y
B Z
B Z
C X
A Z
C Y
C Y
A Y
A X
A Y
C Y
B Y
C Y
A Y
C X
C Y
C Y
C Y
C Y
C Y
C Y
C Y
C Y
C Y
C Y
B Z
B Z
A Y
B Y
C Y
C Y
C X
B Z
A Y
C Y
B Z
C Y
A Y
C X
B X
C Z
A Y
C X
B Y
C X
B Z
A Y
C Y
C Y
C Y
B Z
B Z
A Z
C Z
A Y
B Z
C X
C Y
A Y
C Y
C Y
C X
B Z
C Z
C Y
B Z
A X
B Y
A Z
B X
C X
A Y
C Y
B Z
B Z
C Y
A Y
B Z
B Z
A Y
A Y
B Z
B Z
A X
B Z
C Y
C Z
C Y
C Y
B Z
C Y
A Z
A X
C X
B Y
B Z
C X
B Z
A X
A Y
A Y
B X
B Y
B Z
C X
C X
C X
C Y
C Y
B X
C Y
C Y
A Y
C X
A Y
B Z
A Y
C Y
B X
C X
C Y
A Y
C X
C Y
C Y
B Z
A Y
A Y
A Z
C Y
B Z
A Z
B X
A Z
C Y
B X
A Y
C Y
C Z
A Z
C X
A Y
B X
C Y
C Y
C X
C Z
A Y
A Y
C Y
B Z
C Y
C X
B X
A Y
B Y
B Z
B Z
C Y
C Y
A Y
B Z
B Z
C Y
C Z
C Y
C Y
C X
C X
A Y
B Z
A Y
B Z
B Z
C X
B X
C X
C Y
B X
B Z
C X
C X
C X
B Z
B Y
B Z
C Y
C Y
B Z
B Y
B Z
C Y
B Y
A Y
A Y
B Y
A Y
B Z
A Y
C X
A Y
C Z
B Y
C Y
C X
C X
C Y
C X
C Y
A Y
B Z
C X
C Y
C Y
C Y
C Y
C Y
B Y
A Y
B Z
C Y
A Z
C Y
B Z
C X
C Z
A Y
B Z
C X
C Y
B Z
C Y
A Y
C Y
C X
C X
C Y
A Y
B Y
A X
C Y
C Y
A Y
C Y
A X
C Y
B Y
C X
B Z
B Z
B Y
C Y
C Z
C X
B Z
B Z
B Y
C Z
A Y
C Y
C Y
B Y
C X
A Y
C Y
C Y
A Z
A Y
B Y
C Y
C Y
C X
B X
C Y
A Y
B Z
B Y
A Y
B Y
C Y
C X
C Y
C Y
C X
A Y
B Z
C X
C Z
B X
C Y
C X
C Y
C X
C Y
B Z
A Y
A Y
B Z
C Y
C Y
B Z
C Y
C X
B Z
C Y
B Z
C X
C Y
C X
A Y
C Y
A X
C Y
C X
A Y
C Y
A Y
C Y
A Y
C X
A Y
A Y
A Y
B Y
B Z
C Y
B Z
A Y
C Y
C X
B X
C Y
B Z
B X
B X
B Y
C Y
C X
C Y
C X
B Y
B Z
A Y
B Y
C Y
C Z
A Y
C Y
B Y
A Y
C X
C Y
A Y
C Y
A Z
C Z
C Y
B Z
C Y
C Y
B Y
A Y
C Z
A X
C Y
B Z
C Z
B X
C Y
C X
C X
B Z
B Z
B X
B Y
C Y
B Z
B Z
B Z
A Y
B Z
C Y
C Y
A Y
A X
C Z
A Y
C Z
C Y
C X
C Y
C X
B Z
C Y
A X
B Z
B Y
C Y
B Y
C Y
C Y
A Y
B Z
B Y
C Y
C X
C Y
C Y
B Z
A Y
B X
C Y
C Y
C Y
A Y
C Y
C Y
C X
C Z
C X
B Y
A Y
C Z
B Z
B Z
A Y
C Y
C X
B X
C X
B Z
A Y
C Y
C Y
B Z
A Y
B Y
C X
B Y
B Z
B Z
B Z
B X
B Y
B Z
C X
A Z
A Y
C Y
A Y
C Y
C Y
B Z
B Z
C X
B X
C Y
A Z
C Y
C X
C Y
B Y
C X
B Z
C Y
C X
B Z
A Y
A Y
C Y
A Y
A Z
C Y
A Z
A Y
C Y
C X
B X
C Y
C Y
C X
A Y
C X
C Y
B Z
B X
C Y
A Y
B X
C Y
B Y
C X
A Y
B Z
C Y
C Y
B Z
A Y
A Z
C Y
C Y
C X
A Y
B Z
B Z
B Y
B Z
A Y
C Y
A Y
B Y
A Y
C X
C Y
A Z
A Z
B Y
A Y
C Y
A X
C Y
C Y
B Z
C Y
C X
C Y
A Y
C Y
C Y
C Y
C Y
A X
C Y
B Y
C Y
A Y
C Y
C X
C X
C Z
B Z
A Y
B X
C Y
C Y
C Y
A Y
B Z
B Z
C Y
A Y
B Z
A Y
C X
C X
A Y
C Z
C Y
B Z
B Z
A Y
A Y
C Y
A Y
C Y
A Y
B Z
C X
C Y
C Y
A Y
C Y
A Y
A Z
B Y
C Y
C X
A Y
C X
A X
C Y
C Y
B Y
C Y
B Z
A Y
C X
B Z
A Y
C X
B Z
A Y
C Y
B Z
A Y
B Z
C X
C Y
C X
C Y
B Z
C Y
B Y
C Z
B Z
C Y
C Y
A X
C X
A Y
C Y
B Z
A Y
A Z
B Z
C Y
C Y
A Y
A Y
A Y
C X
C Y
A Z
C Y
C Z
A Y
A Y
C Y
C X
C X
C Z
C Y
C Z
B Z
A Y
B Z
B Z
C X
A Y
B Y
A Y
A Z
A Y
C Y
C Y
C Z
A Y
C Y
B Y
C X
C X
B X
C Y
A Y
C Z
A Y
B Z
B Y
C Y
A Y
A Y
C Y
C Y
C Y
C Y
C Y
C Y
C Y
A Y
B Y
A Y
B Y
B Y
C X
C X
C Y
A Y
C Y
C Y
C Z
C Y
C Y
B Y
C Y
B Y
A Y
C Y
B Z
C X
C Y
A Z
A Z"""
