package net.matsuda.util

import processing.core.PApplet

/* processing-ktx */

abstract class MyPApplet : PApplet() {

    fun drawShape(block: () -> Unit) {
        beginShape()
        block()
        endShape()
    }

    override fun rect(x: Float, y: Float, width: Float, height: Float) {
        super.rect(x, y, width, height)
    }
}

fun Boolean.doTrue(block: () -> Unit): Boolean {

    if (this) block()

    return this
}

fun Boolean.doFalse(block: () -> Unit): Boolean {

    if (this.not()) block()

    return this
}

fun <T> MutableList<T>.push(value: T) {
    add(0, value)
    removeAt(size - 1)
}

fun <T : Number> MutableList<T>.removeLessThan(value: T): Int {
    val firstList = this.toMutableList()
    val sortedList = this.sortedBy { it.toByte() }.distinct()

    for (item in sortedList) {
        if (item.toByte() <= value.toByte()) {
            while (remove(item)) continue
        } else {
            break
        }
    }

    return firstList.size - this.size
}

fun <T : Number> MutableList<T>.removeMoreThan(value: T): Int {
    val firstList = this.toMutableList()
    val sortedList = this.sortedBy { it.toByte() }.reversed().distinct()

    for (item in sortedList) {
        if (item.toByte() >= value.toByte()) {
            while (remove(item)) continue
        } else {
            break
        }
    }

    return firstList.size - this.size
}

fun MutableList<Int>.getNextDownIndex(startIndex: Int): Int? {
    val startIndex = if (startIndex == 0) 1 else startIndex

    for (i in startIndex until size) {
        if (this[i - 1] > this[i]) return i - 1
    }

    return null
}

fun MutableList<Int>.getNextUpIndex(startIndex: Int): Int? {
    val startIndex = if (startIndex == 0) 1 else startIndex

    for (i in startIndex until size) {
        if (this[i - 1] < this[i]) return i - 1
    }

    return null
}

fun MutableList<Int>.flip(): MutableList<Int> {
    val max = this.max()!!
    return this.map { Math.abs(it - max) }.toMutableList()
}

fun <T> List<T>.forEachWithNextWithIndex(block: ((index: Int, item: T, nextItem: T) -> Unit)) {

    for (i in (0 until this.size - 1)) {

        block(i, this[i], this[i + 1])
    }
}

fun <T> T.println(): T = this.also { kotlin.io.println(this) }

fun <T> Iterable<T>.findIndex(block: (T) -> Boolean): List<Int> {
    val result = mutableListOf<Int>()

    for ((index, item) in this.withIndex()) {
        if (block(item)) {
            result.add(index)
        }
    }

    return result
}

fun List<Int>.getBetween(): List<Int> {
    val result = mutableListOf<Int>()

    for (i in 0 until this.size - 1) {
        result.add(Math.abs(this[i] - this[i + 1]))
    }

    return result
}