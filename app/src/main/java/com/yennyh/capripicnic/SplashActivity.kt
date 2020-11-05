package com.yennyh.capripicnic

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.yennyh.capripicnic.ui.activities.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setContentView(R.layout.activity_splash)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out) //carga animación
        logo_imageView.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.splash_in
            )
        )  //asincronico, pero con los tiempos de cada uno se sincroniza

        Handler(Looper.getMainLooper()).postDelayed({
            logo_imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_out))
            Handler(Looper.getMainLooper()).postDelayed({
                logo_imageView.visibility = View.GONE
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 500)
        }, 1500)
    }
}