package com.rahat.pro_hero.codingtest.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)

