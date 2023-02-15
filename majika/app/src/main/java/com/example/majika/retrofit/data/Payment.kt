package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class Payment (
    @SerializedName("status") val status: String?
)