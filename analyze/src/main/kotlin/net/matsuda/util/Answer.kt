package net.matsuda.util

/**
 * JANコードから1～4の数字を生成する
 */
fun getCode(value: String): List<Int> {
    if (value.length != 13) throw IllegalStateException()

    val one = value.substring(0, 1).toInt()
    val two = value.substring(1, 7).map { it.toString().toInt() }
    val three = value.substring(7, 13).map { it.toString().toInt() }

    val oneDigitPattern = oneDigitPatterns[one]
    return mutableListOf<Int>().apply {
        addAll(arrayListOf(1, 1, 1))
        two.forEachIndexed { index, item ->

            addAll(if (oneDigitPattern!![index]) {
                codeList[item]!!.reversed()
            } else {
                codeList[item]!!
            })
        }
        addAll(arrayListOf(1, 1, 1, 1, 1))

        three.forEach { item ->
            addAll(codeList[item]!!)
        }

        addAll(arrayListOf(1, 1, 1))
    }
}

val codeList: Map<Int, List<Int>> = mutableMapOf(
        0 to listOf(3, 2, 1, 1),
        1 to listOf(2, 2, 2, 1),
        2 to listOf(2, 1, 2, 2),
        3 to listOf(1, 4, 1, 1),
        4 to listOf(1, 1, 3, 2),
        5 to listOf(1, 2, 3, 1),
        6 to listOf(1, 1, 1, 4),
        7 to listOf(1, 3, 1, 2),
        8 to listOf(1, 2, 1, 3),
        9 to listOf(3, 1, 1, 2)
)

/**
 * reverse -> true
 */
val oneDigitPatterns: Map<Int, List<Boolean>> = mutableMapOf(
        0 to listOf(false, false, false, false, false, false),
        1 to listOf(false, false, true, false, true, true),
        2 to listOf(false, false, true, true, false, true),
        3 to listOf(false, false, true, true, true, false),
        4 to listOf(false, true, false, false, true, true),
        5 to listOf(false, true, true, false, false, true),
        6 to listOf(false, true, true, true, false, false),
        7 to listOf(false, true, false, true, false, true),
        8 to listOf(false, true, false, true, true, false),
        9 to listOf(false, true, true, false, true, false)
)