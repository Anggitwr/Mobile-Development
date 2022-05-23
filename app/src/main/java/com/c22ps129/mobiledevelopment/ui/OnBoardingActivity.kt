package com.c22ps129.mobiledevelopment.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()
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
}