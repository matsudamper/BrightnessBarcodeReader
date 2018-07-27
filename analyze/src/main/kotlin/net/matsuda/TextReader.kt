package net.matsuda

import processing.serial.Serial

class TextReader(private val port: Serial) {

    var readValues = ""

    fun read(): List<Int> {
        val result = mutableListOf<Int>()

        if (port.available() <= 0) return result

        readValues += port.readString()

        while (true) {
            val index = readValues.indexOf("\n")
            if (index < 0) break

            val read = readValues.substring(0, index)
            readValues = readValues.removeRange(0..index)

            val readInt = read.toIntOrNull() ?: continue
            result.add(readInt)
        }

        return result
    }
}