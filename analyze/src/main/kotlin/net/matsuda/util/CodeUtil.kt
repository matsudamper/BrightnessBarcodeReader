package net.matsuda.util

fun checkCode(code : String) : Boolean{

    val j偶数 = code
            .filterIndexed { index, c -> (index + 1) % 2 == 0 }
            .map { it.toInt() }
            .sum() * 3

    val j奇数 = code
            .filterIndexed { index, c -> (index + 1) % 2 == 1 }
            .map { it.toInt() }
            .sum()

    return (j偶数 + j奇数) % 10 == 0
}