package com.c22ps129.mobiledevelopment.ui.signup

import androidx.lifecycle.ViewModel
import com.c22ps129.mobiledevelopment.UserRepo

class SignupViewModel(private val userRepo: UserRepo) : ViewModel() {

    fun uSignup(name: String,email: String, password: String) = userRepo.uSignup(name, email, password)
}