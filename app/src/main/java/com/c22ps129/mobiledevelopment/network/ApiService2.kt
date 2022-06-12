package com.c22ps129.mobiledevelopment.network

import com.c22ps129.mobiledevelopment.response.OcrResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService2 {

    @Multipart
    @POST("detect")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<OcrResponse>
}