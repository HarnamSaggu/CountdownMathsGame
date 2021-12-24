fun main(args: Array<String>) {
	println(getNumbers(2))
	println(getTarget())
}

fun getTarget(): Int = (0 until 1000).random()

fun getNumbers(bigNumberCount: Int): List<Int> {
	val smallBois = ((1..10) + (1..10)).toMutableList()
	val bigBois = intArrayOf(25, 50, 75, 100)
	smallBois.shuffle()
	bigBois.shuffle()
	return bigBois.take(bigNumberCount) + smallBois.take(6 - bigNumberCount)
}

/*
Take 0-4 big numbers, the other numbers small = 6 numbers
Either add, sub, mult, div them to make the target 3-digit number
	sub: cannot produce a negative number
	div: must be a perfect divisor
 */
