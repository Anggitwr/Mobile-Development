package com.c22ps129.mobiledevelopment.response

import com.google.gson.annotations.SerializedName

data class OcrResponse (
    @field:SerializedName("prediction")
    val prediction: ListPredict
)

data class ListPredict(
//    @field:SerializedName("box")
    val box: List<ListBox>,

//    @field:SerializedName("text")
    val text: String
)

data class ListBox(
    val satu: Int,
    val dua: Int,
    val tiga: Int,
    val empat: Int
)