package com.c22ps129.mobiledevelopment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c22ps129.mobiledevelopment.data.Ocr
import com.c22ps129.mobiledevelopment.data.User
import com.c22ps129.mobiledevelopment.data.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference): ViewModel() {

    fun getUser():LiveData<User>{
        return pref.getUser().asLiveData()
    }

    fun auth(): LiveData<Boolean>{
        return pref.authLogin().asLiveData()
    }

    fun saveOcr(ocr: Ocr){
        viewModelScope.launch {
            pref.saveOcr(ocr)
        }
    }

}