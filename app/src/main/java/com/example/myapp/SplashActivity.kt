package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.myapp.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private  lateinit var  timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        timer = object : CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}