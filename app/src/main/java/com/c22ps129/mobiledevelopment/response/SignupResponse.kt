package com.c22ps129.mobiledevelopment.response

import com.google.gson.annotations.SerializedName

data class SignupResponse (
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

)