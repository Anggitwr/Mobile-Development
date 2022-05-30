package com.c22ps129.mobiledevelopment

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c22ps129.mobiledevelopment.network.ApiService
import com.c22ps129.mobiledevelopment.network.SystemResults
import com.c22ps129.mobiledevelopment.response.SignupResponse
import java.util.prefs.Preferences

class UserRepo private constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: ApiService
){
    fun uSignup(name: String, email: String, password: String) : LiveData<SystemResults<SignupResponse>> = liveData {
        emit(SystemResults.Loading)
        try {
            val result = apiService.uSignup(name, email, password)
            emit(SystemResults.Success(result))
        }catch (e : Exception){
            e.printStackTrace()
            emit(SystemResults.Error(e.message.toString()))
        }
    }

}