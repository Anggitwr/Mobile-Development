package com.c22ps129.mobiledevelopment.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c22ps129.mobiledevelopment.data.User
import com.c22ps129.mobiledevelopment.data.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

}