package net.matsuda

import org.krysalis.barcode4j.impl.upcean.EAN13
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
import java.awt.Canvas
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.*
import javax.imageio.ImageIO

fun main(args: Array<String>) = Make().main(args)

class Make {

    companion object {
        const val path = ""
        const val outPath = ""
        const val outName = "out"

        const val width = 2480
        const val height = 3608
    }

    val random = Random()

    fun main(arg: Array<String>) {

        val outImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB).toWhite()
        val outGraphics = outImage.graphics

        var nextY = 100

        for (i in 0..8) {

            (1..12)
                    .map { random.nextInt(10) }
                    .run { joinToString("") }
                    .let { code ->
                        
                        ByteArrayOutputStream().use { byteArrayOut ->

                            val qrImage = BitmapCanvasProvider(
                                    byteArrayOut, "image/x-png", 400,
                                    BufferedImage.TYPE_BYTE_BINARY, false, 0).edit {

                                val ean13 = EAN13()
                                ean13.generateBarcode(it, code)
                            }.bufferedImage

                            val qrWidth = width
                            val qrHeight = (width / qrImage.width) * qrImage.height

                            BufferedImage(qrWidth, qrHeight / 3, BufferedImage.TYPE_INT_RGB).toWhite().apply {
                                graphics.drawImage(qrImage,
                                        0, -(qrHeight / 3 * 2),
                                        qrWidth, qrHeight,
                                        null)

                                flush()
                            }.let {
                                outGraphics.drawImage(it,
                                        0, nextY,
                                        null)

                                nextY += it.height
                            }
                        }
                    }
        }

        outImage.flush()

        ByteArrayOutputStream().use { byteArrayOut ->
            BufferedOutputStream(byteArrayOut).use {
                ImageIO.write(outImage, "jpg", it)
            }

            FileOutputStream("${outPath}\\\${outName}.png").use {
                it.write(byteArrayOut.toByteArray())
            }
        }
    }
}

fun BitmapCanvasProvider.edit(block: (it: BitmapCanvasProvider) -> Unit): BitmapCanvasProvider {
    block(this)
    finish()

    return this
}

fun BufferedImage.toBlackOrWhite(): BufferedImage {

    for (x in 0 until width) {
        for (y in 0 until height) {

            val rgb = getRGB(x, y)

            val ave = (rgb.getR() + rgb.getG() + rgb.getB()) / 3

            if (ave < 200) {
                setRGB(x, y, Color(0, 0, 0).rgb)
            } else {
                setRGB(x, y, Color(255, 255, 255).rgb)
            }
        }
    }

    return this
}

fun BufferedImage.toWhite(): BufferedImage {

    for (x in 0 until width) {
        for (y in 0 until height) {
            setRGB(x, y, Color(255, 255, 255).rgb)
        }
    }

    return this
}

fun Int.getR() = this and 0xFF
fun Int.getG() = this shr 8 and 0xFF
fun Int.getB() = this shr 16 and 0xFF
fun Int.getA() = this shr 24 and 0xFF