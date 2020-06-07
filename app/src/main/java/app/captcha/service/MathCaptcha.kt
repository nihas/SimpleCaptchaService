package app.captcha.service

import android.graphics.*
import android.opengl.ETC1.getWidth
import java.util.*
import kotlin.collections.ArrayList

/*
 Created by NIHAS @ 07/06/2020
 */
class MathCaptcha(width: Int, height: Int, opt: MathOptions) : CaptchaBase(){
    protected var options: MathOptions? = null

    enum class MathOptions {
        PLUS_MINUS, PLUS_MINUS_MULTIPLY
    }

    init {
        heightRect = height
        setWidth(width)
        options = opt
        usedColors = ArrayList()
        image = image()
    }

    override fun image(): Bitmap? {
        var one = 0
        var two = 0
        var math = 0

        val gradient = LinearGradient(
            0F,
            0F,
            widthRect.toFloat() / 2,
            this.heightRect.toFloat() / 2,
            color(),
            color(),
            Shader.TileMode.MIRROR
        )
        val p = Paint()
        p.setDither(true)
        p.setShader(gradient)
        val bitmap = Bitmap.createBitmap(widthRect, this.heightRect, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        c.drawRect(0F, 0F, widthRect.toFloat(), this.heightRect.toFloat(), p)

        val fontGrad = LinearGradient(
            0F,
            0F,
            widthRect.toFloat() / 2,
            this.heightRect.toFloat() / 2,
            color(),
            color(),
            Shader.TileMode.CLAMP
        )
        val tp = Paint()
        tp.isDither = true
        tp.shader = fontGrad
        tp.textSize = widthRect.toFloat() / this.heightRect.toFloat() * 20
        val r = Random(System.currentTimeMillis())

        one = r.nextInt(9) + 1
        two = r.nextInt(9) + 1
        math = r.nextInt(if (options === MathOptions.PLUS_MINUS_MULTIPLY) 3 else 2)
        if (one < two) {
            val temp = one
            one = two
            two = temp
        }

        when(math){
            0 -> this.answer = "${(one + two)}"
            1 -> this.answer = "${(one - two)}"
            2 -> this.answer = "${(one * two)}"
        }

        val data = charArrayOf(
            one.toString().toCharArray()[0],
            oper(math),
            two.toString().toCharArray()[0]
        )

        for (i in 0 until data.size) {
            x += 30 + Math.abs(r.nextInt()) % 65
            y = 50 + Math.abs(r.nextInt()) % 50
            val cc = Canvas(bitmap)
            if (i != 1) tp.textSkewX = r.nextFloat() - r.nextFloat()
            cc.drawText(data, i, 1, x.toFloat(), y.toFloat(), tp)
            tp.textSkewX = 0f
        }
        return bitmap
    }

    fun oper(math: Int?): Char {
        when (math) {
            0 -> return '+'
            1 -> return '-'
            2 -> return '*'
        }
        return '+'
    }

}