package com.c22ps129.mobiledevelopment.response

import com.google.gson.annotations.SerializedName

data class OcrResponse (
    @field:SerializedName("prediction")
    val prediction: List<Int?>? = null
)