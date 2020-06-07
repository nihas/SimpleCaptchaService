package app.captcha.service

import android.graphics.*
import java.io.CharArrayWriter
import kotlin.random.Random

/*
 Created by NIHAS @ 07/06/2020
 */
class TextCaptcha(width: Int, height: Int,wordLength: Int,opt: TextOptions)  : CaptchaBase() {
    protected var options: TextOptions? = null
    private var wordLength = 0
    private var mCh = 0.toChar()

    constructor(wordLength: Int,opt: TextOptions): this(0,0,wordLength,opt)

    enum class TextOptions {
        UPPERCASE_ONLY, LOWERCASE_ONLY, NUMBERS_ONLY, LETTERS_ONLY, NUMBERS_AND_LETTERS
    }

    init {
        setHeight(height)
        setWidth(width)
        this.options = opt
        usedColors = ArrayList()
        this.wordLength = wordLength;
        this.image = image()
    }

    override fun image(): Bitmap? {
        val gradient = LinearGradient(
            0F,
            0F,
            widthRect.toFloat() / wordLength,
            heightRect.toFloat() / 2,
            color(),
            color(),
            Shader.TileMode.MIRROR
        )
        val p = Paint()
        p.setDither(true)
        p.setShader(gradient)
        val bitmap =
            Bitmap.createBitmap(widthRect, heightRect, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        c.drawRect(0F, 0F, widthRect.toFloat(), heightRect.toFloat(), p)
        val tp = Paint()
        tp.setDither(true)
        tp.setTextSize(widthRect.toFloat() / heightRect.toFloat() * 20)

        var randomVal = Random(System.currentTimeMillis())
        val cab = CharArrayWriter()
        this.answer = "";
        for (i in 0..wordLength){
            var ch: Char = ' '
            when(options){
                TextOptions.UPPERCASE_ONLY -> ch = ((randomVal.nextInt(91 - 65) + (65)).toChar())
                TextOptions.LOWERCASE_ONLY -> ch = ((randomVal.nextInt(123 - 97) + (97)).toChar())
                TextOptions.NUMBERS_ONLY -> ch = ((randomVal.nextInt(58 - 49) + (49)).toChar())
                TextOptions.LETTERS_ONLY -> ch = getLetters(randomVal)
                TextOptions.NUMBERS_AND_LETTERS -> ch = getLettersNumbers(randomVal)
            }
            cab.append(ch)
            this.answer+= ch
        }
        val data = cab.toCharArray()
        for (i in 0 until data.size) {
            x += (30 - 3 * wordLength + Math.abs(randomVal.nextInt()) % (65 - 1.2 * wordLength)).toInt()
            y = 50 + Math.abs(randomVal.nextInt()) % 50
            val cc = Canvas(bitmap)
            tp.textSkewX = randomVal.nextFloat() - randomVal.nextFloat()
            tp.color = color()
            cc.drawText(data, i, 1, x.toFloat(), y.toFloat(), tp)
            tp.textSkewX = 0f
        }
        return bitmap
    }

    private fun getLetters(r: Random): Char {
        val rint = r.nextInt(123 - 65) + 65
        if (rint in 91..96) getLetters(r) else mCh = rint.toChar()
        return mCh
    }

    private fun getLettersNumbers(r: Random): Char {
        val rint = r.nextInt(123 - 49) + 49
        if (rint in 91..96) getLettersNumbers(r) else if (rint in 58..64) getLettersNumbers(
            r
        ) else mCh = rint.toChar()
        return mCh
    }

}