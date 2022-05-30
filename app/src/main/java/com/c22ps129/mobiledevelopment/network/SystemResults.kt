package com.c22ps129.mobiledevelopment.network

sealed class SystemResults<out R> private constructor() {
    data class Success<out T>(val data: T) : SystemResults<T>()
    data class Error(val error: String) : SystemResults<Nothing>()
    object Loading : SystemResults<Nothing>()

}