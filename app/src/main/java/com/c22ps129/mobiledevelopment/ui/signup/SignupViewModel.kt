package com.c22ps129.mobiledevelopment.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c22ps129.mobiledevelopment.data.User
import com.c22ps129.mobiledevelopment.data.UserPreference
import kotlinx.coroutines.launch

class SignupViewModel(private val pref:UserPreference) : ViewModel() {

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

//    fun uSignup(name: String,email: String, password: String) = userRepo.uSignup(name, email, password)
}