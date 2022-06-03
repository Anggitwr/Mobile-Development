package com.c22ps129.mobiledevelopment.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps129.mobiledevelopment.data.UserPreference
import com.c22ps129.mobiledevelopment.ui.login.LoginViewModel
import com.c22ps129.mobiledevelopment.ui.main.MainViewModel
import com.c22ps129.mobiledevelopment.ui.profile.ProfileViewModel
import com.c22ps129.mobiledevelopment.ui.signup.SignupViewModel

class ViewModelFactory(private val userPref: UserPreference): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{

            modelClass.isAssignableFrom(SignupViewModel::class.java)-> {
                SignupViewModel(userPref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java)-> {
                LoginViewModel(userPref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java)-> {
                MainViewModel(userPref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java)-> {
                ProfileViewModel(userPref) as T
            }
            else -> throw IllegalArgumentException("unknown ViewModel class : " + modelClass.name)
        }
    }
}