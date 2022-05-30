package com.c22ps129.mobiledevelopment.network

import com.c22ps129.mobiledevelopment.response.LoginResponse
import com.c22ps129.mobiledevelopment.response.SignupResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    @FormUrlEncoded
    suspend fun uSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse

    @POST("login")
    @FormUrlEncoded
    suspend fun uLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}