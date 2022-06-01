package com.c22ps129.mobiledevelopment.ui.signup

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.c22ps129.mobiledevelopment.R
import com.c22ps129.mobiledevelopment.data.User
import com.c22ps129.mobiledevelopment.data.UserPreference
import com.c22ps129.mobiledevelopment.databinding.ActivitySignupBinding
import com.c22ps129.mobiledevelopment.network.ApiConfig
import com.c22ps129.mobiledevelopment.response.SignupResponse
import com.c22ps129.mobiledevelopment.ui.customview.EditTextPassword
import com.c22ps129.mobiledevelopment.utils.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var passwordAlert: EditTextPassword
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUp()
        setupView()
        setupViewModel()
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

//    private fun setupViewModel(){
//
//        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
//        signupViewModel = ViewModelProvider(this, factory)[SignupViewModel::class.java]
//
//    }

    private fun setupViewModel() {
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignupViewModel::class.java]
    }
    private fun action(){
        passwordAlert = binding.etPassword
    }

    private fun signUp(){
        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
                val client = ApiConfig.getApiService().uSignup(name, email, password)
                client.enqueue(object : Callback<SignupResponse> {
                    override fun onResponse(
                        call: Call<SignupResponse>,
                        response: Response<SignupResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && !responseBody.success) {
                                signupViewModel.saveUser(User(name, email, false))
                                Toast.makeText(this@SignupActivity, getString(R.string.success_signup), Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        } else {
                            Toast.makeText(this@SignupActivity, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                        Toast.makeText(this@SignupActivity, getString(R.string.failedRetrofit), Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, getString(R.string.sign_up), Toast.LENGTH_SHORT).show()
            }

        }
    }
}