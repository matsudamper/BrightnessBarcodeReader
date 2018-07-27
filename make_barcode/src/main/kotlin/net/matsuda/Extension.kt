package net.matsuda

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