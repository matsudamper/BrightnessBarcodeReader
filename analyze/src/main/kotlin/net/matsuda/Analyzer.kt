package net.matsuda

import net.matsuda.util.*

class Analyzer(inputValues: List<Int>) {

    private lateinit var topChangedValue: List<Int>
    private lateinit var bottomChangedValue: List<Int>

    @Throws(Exception::class)
    fun getCode(): String {
        // 上下データを取得
        val upDownIndex = getUpDownIndex(log)
        val topList = upDownIndex.filterIndexed { index, i -> index % 2 == 0 }
        val bottomList = upDownIndex.filterIndexed { index, i -> index % 2 == 1 }

        // 変化が大きかった値を取得
        topChangedValue = getLargeChangedValue(topList)
        bottomChangedValue = getLargeChangedValue(bottomList)


        val result = upDownIndex.map { log[it] }
                // 1~3に復号
                .mapIndexed { index, item ->
                    when (index % 2 == 0) {
                        true -> getTopSize(item)
                        false -> getBottomSize(item)
                    }
                }
                // 1~4に復号
                .let { result ->
                    if (result.size != 59) throw IllegalStateException("sizeが59ではありません。 size = ${result.size}")

                    kotlin.io.println("size = ${result.size}")
                    kotlin.io.println("1-3 : ${result.map { String.format("%03d", it) }}")

                    result.subList(0, 3).println()
                    for (i in 0 until 6) {
                        val pivot = 3 + i * 4
                        kotlin.io.println("${result.subList(pivot, pivot + 4)} = ${result.subList(pivot, pivot + 4).sum()}")
                    }
                    "=====".println()
                    result.subList(27, 32).println()
                    "=====".println()
                    for (i in 0 until 6) {
                        val pivot = 32 + i * 4
                        kotlin.io.println("${result.subList(pivot, pivot + 4)} = ${result.subList(pivot, pivot + 4).sum()}")
                    }
                    "=====".println()
                    result.subList(56, 59).println()

                    System.out.flush()

                    mutableListOf<Int>().apply {
                        addAll(result.subList(0, 3))
                        addAll(correction(result.subList(3, 27)).flatten())
                        addAll(result.subList(27, 32))
                        addAll(correction(result.subList(32, 56)).flatten())
                        addAll(result.subList(56, 59))
                    }
                }

        kotlin.io.println("全て : ${result.map { String.format("%03d", it) }}")
        kotlin.io.println()

        kotlin.io.println("上   : ${topList.map { log[it] }.map { String.format("%03d", it) }}")
        kotlin.io.println("上   : ${topList.map { log[it] }.map { getTopSize(it) }.map { String.format("%03d", it) }}")
        kotlin.io.println()

        kotlin.io.println("下   : ${bottomList.map { log[it] }.map { String.format("%03d", it) }}")
        kotlin.io.println("下   : ${bottomList.map { log[it] }.map { getBottomSize(it) }.map { String.format("%03d", it) }}")
        kotlin.io.println()

        kotlin.io.println("上ソート : ${topList.map { log[it] }.distinct().sorted()}")
        kotlin.io.println("上ソート : $topChangedValue")

        kotlin.io.println("下ソート : ${bottomList.map { log[it] }.distinct().sorted()}")
        kotlin.io.println("下ソート : $bottomChangedValue")

        return barToNumber(result)
    }

    val log: List<Int> = inputValues
            .toMutableList()
            .let { log ->

                "===========".println()
                log.size.println()

                // 周りで平均を取る
                log.mapIndexed { index, i ->

                    val range = 3
                    (index - range..index + range)
                            .map { Math.abs(it) }
                            .map {
                                when (log.size <= it) {
                                    true -> {
                                        (log.size - 1) - (it - log.size)
                                    }
                                    false -> it
                                }
                            }
                            .map { log[it] }
                            .sum() / (range * 2 + 1)

                }.toMutableList()
            }
            .flip() // 反転
            .also { log ->
                // 先頭と末尾をそれぞれ変化がプラスの時にカット

                fun removeStart() {
                    val rangeAverage = (0 until log.size - 10 step 10)
                            .map { index ->
                                (index until index + 10).map { log[it] }.average()
                            }

                    rangeAverage.iterator().let { itelater ->
                        if (itelater.hasNext().not()) return@let

                        var beforeValue = itelater.next()
                        var counter = 0
                        while (itelater.hasNext()) {
                            counter++
                            val value = itelater.next()

                            if (value - beforeValue > 5) {

                                counter *= 10
                                while (counter-- > 0) log.removeAt(0)

                                return@let
                            }

                            beforeValue = value
                        }
                    }
                }

                removeStart()
                log.reverse()
                removeStart()
                log.reverse()
            }
            .apply {
                var upDownIndex = getUpDownIndex(this)

                // 59個にしないといけない
                while (upDownIndex.size > 60) {

                    // 差分 値一覧
                    val sortedDiff = (0 until upDownIndex.size - 1)
                            .map { upDownIndex[it + 1] - upDownIndex[it] }
                            .sorted()
                            .distinct()


                    // 変化が近すぎる一覧
                    val nearIndexes = (0 until upDownIndex.size - 1).mapNotNull { index ->
                        // 一番小さい差分から処理
                        when (sortedDiff[0] == upDownIndex[index + 1] - upDownIndex[index]) {
                            true -> IntRange(upDownIndex[index], upDownIndex[index + 1])
                            false -> null
                        }
                    }

                    // 周りの平均で埋める
                    for (nearIndex in nearIndexes) {
                        kotlin.io.println("$nearIndex : ${this[nearIndex.first - 1]} - ${this[nearIndex.first]} - ${this[nearIndex.last]} - ${this[nearIndex.last + 1]}")
                        val ave = (this[nearIndex.first - 1] + this[nearIndex.last + 1]) / 2

                        nearIndex.forEach {
                            this[it] = ave
                        }
                    }

                    kotlin.io.println()

                    upDownIndex = getUpDownIndex(this)

                    break
                }
            }

    private fun getTopSize(size: Int) = when (size) {
        in 0 until topChangedValue[0] -> 1
        in topChangedValue[0] until topChangedValue[1] -> 2
        else -> 3
    }

    private fun getBottomSize(size: Int) = when (size) {
        in 0 until bottomChangedValue[0] -> 3
        in bottomChangedValue[0] until bottomChangedValue[1] -> 2
        else -> 1
    }

    private fun getUpDownIndex(list: List<Int>): MutableList<Int> {
        val list = list.toMutableList()

        val result = mutableListOf<Int>()

        var index = 0
        while (true) {
            val downIndex = list.getNextDownIndex(index) ?: break
            result.add(downIndex)
            index = downIndex + 1

            val upIndex = list.getNextUpIndex(index) ?: break
            result.add(upIndex)
            index = upIndex + 1
        }

        return result
    }

    private fun getLargeChangedValue(list: List<Int>): List<Int> {
        val diffList = list
                .map { log[it] }
                .distinct().sorted()// 高さを並べる

        val diffLargeList =
                diffList
                        .getBetween()// 間を取得
                        .sorted().reversed()// 間が大きいものを先頭へ


        val changeValue = mutableListOf<Int>()
        for (diff in diffLargeList.subList(0, 2)) {
            val iterator = diffList.iterator()
            var beforeItem = iterator.next()
            while (iterator.hasNext()) {
                val item = iterator.next()

                if (item - beforeItem == diff) {
                    changeValue.add(item)
                }

                beforeItem = item
            }
        }

        return changeValue.sorted()
    }

    fun correction(list: List<Int>): List<List<Int>> {
        return (0 until list.size step 4)
                .map { list.subList(it, it + 4) }
                .map {
                    when (it.sum()) {
                        7 -> it
                        6 -> {
                            val deleteIndex = it.indexOf(3)
                            it.toMutableList().apply {
                                removeAt(deleteIndex)
                                add(deleteIndex, 4)
                            }
                        }
                        else -> throw  IllegalStateException("sum = ${it.sum()}")
                    }
                }
    }


    private fun split4(list: List<Int>): List<List<Int>> {
        return (0 until list.size step 4)
                .map { list.subList(it, it + 4) }
    }

    private fun barToNumber(list: List<Int>): String {
        if (list.size != 59) throw IllegalStateException("require size is 59. List size is ${list.size}")

        val firstHalfList = list.subList(3, 27).let(::split4).println()
        val latterHalfList = list.subList(32, 56).let(::split4).println()

        val firstPattern = mutableListOf<Boolean>()
        val firstHalf = firstHalfList.map {
            for ((key, value) in codeList) {
                if (it == value) {
                    firstPattern.add(false)
                    return@map key
                }
                if (it == value.reversed()) {
                    firstPattern.add(true)
                    return@map key
                }
            }

            throw IllegalStateException()
        }.println()

        val latterHalf = latterHalfList.map {
            for ((key, value) in codeList) {
                if (it == value) return@map key
            }

            throw IllegalStateException()
        }.println()

        for ((key, value) in oneDigitPatterns) {
            if (value == firstPattern) {
                return key.toString() + firstHalf.joinToString("") + latterHalf.joinToString("")
            }
        }

        throw IllegalStateException()
    }
}