import kotlin.math.pow

fun main() {
	val pool = getNumbers(2)
	val target = getTarget()
	println(pool)
	println(target)
	solve(pool, target).forEach { println(it) }
}

fun solve(pool: List<Int>, target: Int): MutableList<String> {
	val perms = permuteList(pool)
	val results = mutableListOf<String>()
	for (i in perms.indices) {
		keyLoop@ for (decimalKey in (0 until 4.0.pow(5.0).toInt())) {
			val perm = perms[i].toMutableList()
			val base4Key = Integer.parseInt(decimalKey.toString(), 10).toString(4).padStart(5, '0')
			var result = perm.first()
			perm.removeAt(0)
			for (indvKey in base4Key) {
				when (indvKey) {
					'0' -> {
						result += perm.first()
						perm.removeAt(0)
					}

					'1' -> {
						if (result - perm.first() >= 0) {
							result -= perm.first()
							perm.removeAt(0)
						} else {
							continue@keyLoop
						}
					}

					'2' -> {
						result *= perm.first()
						perm.removeAt(0)
					}

					'3' -> {
						if (result % perm.first() == 0) {
							result /= perm.first()
							perm.removeAt(0)
						} else {
							continue@keyLoop
						}
					}
				}
			}
			if (result == target) {
				val operatorKey =
					base4Key.replace("0".toRegex(), "+").replace("1".toRegex(), "-").replace("2".toRegex(), "*")
						.replace("3".toRegex(), "/")
				val digits = perms[i]
				results.add("((((${digits[0]} ${operatorKey[0]} ${digits[1]}) ${operatorKey[1]} ${digits[2]}) ${operatorKey[2]} ${digits[3]}) ${operatorKey[3]} ${digits[4]}) ${operatorKey[4]} ${digits[5]}")
			}
		}
	}
	return results
}

fun getTarget(): Int = (0 until 1000).random()

fun getNumbers(bigNumberCount: Int): List<Int> {
	val smallBois = ((1..10) + (1..10)).toMutableList()
	val bigBois = intArrayOf(25, 50, 75, 100)
	smallBois.shuffle()
	bigBois.shuffle()
	return bigBois.take(bigNumberCount) + smallBois.take(6 - bigNumberCount)
}

fun permuteList(list: List<Int>): List<List<Int>> {
	val endList = mutableListOf<List<Int>>()
	for (i in list.indices) {
		val reducedList = list.subList(0, i).toMutableList()
		if (i + 1 < list.size) reducedList.addAll(list.subList(i + 1, list.size))
		val tail = mutableListOf<List<Int>>()
		tail.add(listOf(list[i]))
		if (reducedList.size == 0) return tail
		val perms = permuteList(reducedList)
		for (perm in perms) {
			endList.add(listOf(list[i]) + perm)
		}
	}
	return endList
}
