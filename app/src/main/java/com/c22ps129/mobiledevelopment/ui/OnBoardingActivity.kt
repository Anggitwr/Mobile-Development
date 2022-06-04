package com.c22ps129.mobiledevelopment.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.c22ps129.mobiledevelopment.R
import com.c22ps129.mobiledevelopment.databinding.ActivityMainBinding
import com.c22ps129.mobiledevelopment.databinding.ActivityOnBoardingBinding
import com.c22ps129.mobiledevelopment.ui.customview.EditTextPassword
import com.c22ps129.mobiledevelopment.ui.login.LoginActivity
import com.c22ps129.mobiledevelopment.ui.signup.SignupActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()
        action()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun playAnimation(){

        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val tvMagit = ObjectAnimator.ofFloat(binding.tvMagit, View.ALPHA, 1f).setDuration(500)
        val tvSlogan = ObjectAnimator.ofFloat(binding.tvSlogan, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val btnSignUp = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(500)

        val together1 = AnimatorSet().apply {
            playTogether(tvMagit, tvSlogan)
        }

        AnimatorSet().apply {
            playSequentially(together1, image, btnSignUp, btnLogin)
            startDelay = 500
            start()
        }
    }
    private fun action(){

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
//            finish()
        }
    }
}