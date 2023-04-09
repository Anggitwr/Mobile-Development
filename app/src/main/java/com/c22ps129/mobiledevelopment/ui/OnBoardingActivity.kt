package com.c22ps129.mobiledevelopment.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.c22ps129.mobiledevelopment.data.UserPreference
import com.c22ps129.mobiledevelopment.databinding.ActivityOnBoardingBinding
import com.c22ps129.mobiledevelopment.ui.login.LoginActivity
import com.c22ps129.mobiledevelopment.ui.main.MainActivity
import com.c22ps129.mobiledevelopment.ui.main.MainViewModel
import com.c22ps129.mobiledevelopment.ui.signup.SignupActivity
import com.c22ps129.mobiledevelopment.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
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

    private fun authMain(){
        mainViewModel.auth().observe(this){
            if (it){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
        authMain()
//        action()
//
//        binding.btnAdd.setOnClickListener { startGallery() }
//        binding.fltPlay.setOnClickListener { starSpeech() }
    }
}