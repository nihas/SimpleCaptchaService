package app.captcha.service

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
/*
 Created by NIHAS @ 07/06/2020
 */
class MainActivity : AppCompatActivity() {

    var isTextCaptcha = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtCaptcha = TextCaptcha(600, 150, 4, TextCaptcha.TextOptions.LETTERS_ONLY)
        var numCaptcha = MathCaptcha(600, 150, MathCaptcha.MathOptions.PLUS_MINUS)
        captchaImg.setImageBitmap(txtCaptcha.image)

        submitButton.setOnClickListener {
            if(submitButton.text.toString().toLowerCase() == "submit") {
                message.visibility = View.VISIBLE
                if (txtCaptcha.checkAnswer(captchaEdt.text.toString())) {
                    message.setText("Correct Captcha entered")
                    message.setTextColor(Color.parseColor("#07AB0A"))
                    submitButton.text = "Try Another"
                } else {
                    message.text = "Wrong Captcha! Try Again"
                    message.setTextColor(Color.parseColor("#DF0707"))
                }
            }else{
                message.visibility = View.GONE
                captchaEdt.text.clear()
                txtCaptcha = TextCaptcha(600, 150, 4, TextCaptcha.TextOptions.LETTERS_ONLY)
                captchaImg.setImageBitmap(txtCaptcha.image)
                submitButton.text = "Submit"
            }
        }
    }
}
