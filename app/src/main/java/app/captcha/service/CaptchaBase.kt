package app.captcha.service

import android.graphics.Bitmap
import android.graphics.Color

/*
 #Captcha Base class
 Created by NIHAS @ 07/06/2020
 */
abstract class CaptchaBase {
    var image: Bitmap? = null
    var usedColors = ArrayList<Int>()
    var widthRect: Int = 0
    var heightRect: Int = 0
    protected var answer = ""

    protected var x = 0
    protected var y = 0

    protected abstract fun image(): Bitmap?

    fun color(): Int {
        var randomNum = 0
        do{
            randomNum  = (0..9).random()
        }while (usedColors.contains(randomNum))
        usedColors.add(randomNum)
        when(randomNum){
            0 -> return Color.BLACK
            1 -> return Color.BLUE
            2 -> return Color.CYAN
            3 -> return Color.DKGRAY
            4 -> return Color.GRAY
            5 -> return Color.GREEN
            6 -> return Color.MAGENTA
            7 -> return Color.RED
            8 -> return Color.YELLOW
            9 -> return Color.WHITE
            else -> return Color.WHITE
        }
    }

    fun setWidth(width: Int) {
        if (width in 1..9999) {
            widthRect = width
        } else {
            widthRect = 300
        }
    }

    fun setHeight(height: Int) {
        if (height in 1..9999) {
            heightRect = height
        } else {
            heightRect = 100
        }
    }

    fun checkAnswer(ans: String): Boolean {
        return ans == this.answer
    }
}