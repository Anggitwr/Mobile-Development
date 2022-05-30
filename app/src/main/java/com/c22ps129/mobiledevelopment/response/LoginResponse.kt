package com.c22ps129.mobiledevelopment.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)



