package com.c22ps129.mobiledevelopment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c22ps129.mobiledevelopment.data.User
import com.c22ps129.mobiledevelopment.data.UserPreference

class MainViewModel(private val pref: UserPreference): ViewModel() {

    fun getUser():LiveData<User>{
        return pref.getUser().asLiveData()
    }

    fun auth(): LiveData<Boolean>{
        return pref.authLogin().asLiveData()
    }

}