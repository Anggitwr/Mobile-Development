package com.c22ps129.mobiledevelopment.response

import com.google.gson.annotations.SerializedName

data class SignupResponse (
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("data")
    val data: DataResult
)

data class DataResult(
    @field:SerializedName("fieldCount")
    val fieldCount: Int,

    @field:SerializedName("affectedRows")
    val affectedRows: Int,

    @field:SerializedName("insertId")
    val insertId: Int,

    @field:SerializedName("serverStatus")
    val serverStatus: Int,

    @field:SerializedName("warningCount")
    val warningCount: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("protocol41")
    val protocol41: Boolean,

    @field:SerializedName("changedRows")
    val changedRows: Int
)