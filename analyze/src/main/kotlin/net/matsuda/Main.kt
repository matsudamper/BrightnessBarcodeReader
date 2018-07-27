package net.matsuda

import net.matsuda.util.MyPApplet
import net.matsuda.util.checkCode
import net.matsuda.util.flip
import processing.core.PApplet
import processing.core.PConstants
import processing.serial.Serial
import java.awt.Color


fun main(args: Array<String>) = PApplet.main(Main::class.java)
class Main : MyPApplet() {

    private val WINDOW_WIDTH by lazy { displayWidth - 100 }
    private val WINDOW_HEIGHT = 256 * 2

    private val log: MutableList<Int> = mutableListOf()

    private val textReader: TextReader by lazy { TextReader(port) }
    private val port: Serial by lazy {
        try {
            Serial(this, "COM4", 28800)
        } catch (e: RuntimeException) {
            Serial(this, "COM3", 28800)
        }
    }

    override fun settings() = size(WINDOW_WIDTH, WINDOW_HEIGHT)
    override fun setup() {}


    var position = 0
    override fun keyPressed() {
        when (keyCode) {
            PConstants.LEFT -> {
                position += 1
                logDraw()
            }
            PConstants.RIGHT -> {
                position -= 1
                logDraw()
            }
        }

        when (key) {
            PConstants.DELETE -> {
                log.clear()
                background(Color.WHITE.rgb)
                position = 0
            }
            PConstants.ENTER -> {
                fill(0);
                textSize(80f)

                val code = try {
                    Analyzer(log.toMutableList()).also {

                        log.clear()
                        log.addAll(it.log)
                        logDraw()

                    }.getCode()
                } catch (e: Exception) {

                    e.printStackTrace()
                    text("ReadError", 100f, 100f)
                    return
                }

                if (checkCode(code)) {
                    text("OK ${code.substring(0, 1)} ${code.substring(1, 7)} ${code.substring(7, 13)}", 100f, 100f)
                } else {
                    text("Error", 100f, 100f)
                }
            }

        }
        when (keyCode) {
            PConstants.DOWN -> {
            }
        }
    }

    override fun draw() {
        val readValue = textReader.read()
        log.addAll(readValue)
        if (readValue.isEmpty()) return

        logDraw()
    }

    private fun logDraw() {
        background(Color.WHITE.rgb)


        log.toMutableList()
                .flip()
                .map { it.toFloat() * 3 }
                .run {
                    forEachIndexed { index, value ->

                        val color = Color.BLUE
                        fill(color.rgb)
                        stroke(color.rgb)

                        rect(
                                x = index.toFloat() + position * 100,
                                y = WINDOW_HEIGHT.toFloat(),
                                width = 1f,
                                height = -value)
                    }
                }
    }
}