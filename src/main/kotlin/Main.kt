fun main() {
	for (i in 1..10) {
		val pool = getNumbers(2)
		val target = getTarget()
		println(pool)
		println(target)
		println()
		println(solve(pool, target))
		println()
		println()
	}
}

fun solve(pool: List<Int>, target: Int): String {
	while (true) {
		val attempt = attempt(pool)
		if (attempt.first == target) return attempt.second
	}
}

val operatorMap = mapOf(0 to '+', 1 to '-', 2 to '*', 3 to '/')

fun attempt(pool: List<Int>): Pair<Int, String> {
	val localPool = pool.toMutableList()
	var log = ""
	while (localPool.size > 1) {
		val operands = localPool.shuffled().take(2)
		val operator = (0..3).random()
		val result = when (operator) {
			0 -> {
				operands[0] + operands[1]
			}
			1 -> {
				val temp = operands[0] - operands[1]
				if (temp >= 0) temp
				else -1
			}
			2 -> {
				operands[0] * operands[1]
			}
			else -> {
				if (operands[1] == 0) -1
				else {
					val temp = operands[0].toDouble() / operands[1].toDouble()
					if ((temp % 1) == 0.0) temp.toInt()
					else -1
				}
			}
		}
		if (result != -1) {
			localPool.remove(operands[0])
			localPool.remove(operands[1])
			log += "${operands[0]} ${operatorMap[operator]} ${operands[1]} = $result\n"
			localPool.add(result)
		}
	}
	return if (log == "") Pair(-1, "") else Pair(localPool[0], log)
}

fun getTarget(): Int = (0 until 1000).random()

fun getNumbers(bigNumberCount: Int): List<Int> {
	val smallBois = ((1..10) + (1..10)).toMutableList()
	val bigBois = intArrayOf(25, 50, 75, 100)
	smallBois.shuffle()
	bigBois.shuffle()
	return bigBois.take(bigNumberCount) + smallBois.take(6 - bigNumberCount)
}
