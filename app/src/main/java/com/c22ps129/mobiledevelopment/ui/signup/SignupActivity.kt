package com.c22ps129.mobiledevelopment.ui.signup

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.c22ps129.mobiledevelopment.R
import com.c22ps129.mobiledevelopment.databinding.ActivitySignupBinding
import com.c22ps129.mobiledevelopment.network.SystemResults
import com.c22ps129.mobiledevelopment.ui.customview.EditTextPassword

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var passwordAlert: EditTextPassword
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
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

    private fun action(){
        passwordAlert = binding.etPassword
    }

    private fun signUp(){
        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            when{

                name.isEmpty() -> {
                    binding.etName.error = resources.getString(R.string.message_validation, "name")
                }
                email.isEmpty() -> {
                    binding.etEmail.error = resources.getString(R.string.message_validation, "email")
                }
                password.isEmpty() -> {
                    binding.etPassword.error =
                        resources.getString(R.string.message_validation, "password")
                } else -> {

                signupViewModel.uSignup(name, email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is SystemResults.Loading -> {
                                showLoading(true)
                            }
                            is SystemResults.Success -> {
                                showLoading(false)
                                val user = result.data
                                if (user.success) {
                                    Toast.makeText(
                                        this@SignupActivity,
                                        user.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    AlertDialog.Builder(this@SignupActivity).apply {
                                        setTitle("Yeah!")
                                        setMessage("Your account successfully created!")
                                        setPositiveButton("Next") { _, _ ->
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                            }
                            is SystemResults.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.signup_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}