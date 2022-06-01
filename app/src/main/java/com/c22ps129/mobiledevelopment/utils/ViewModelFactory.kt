package com.c22ps129.mobiledevelopment.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps129.mobiledevelopment.data.UserPreference
import com.c22ps129.mobiledevelopment.ui.signup.SignupViewModel

class ViewModelFactory(private val userPref: UserPreference): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{

            modelClass.isAssignableFrom(SignupViewModel::class.java)-> {
                SignupViewModel(userPref) as T
            }
            else -> throw IllegalArgumentException("unknown ViewModel class : " + modelClass.name)
        }
    }
}