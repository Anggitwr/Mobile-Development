package com.c22ps129.mobiledevelopment.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c22ps129.mobiledevelopment.data.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref:UserPreference): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}