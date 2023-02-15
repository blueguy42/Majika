package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class ListMenu (
    @SerializedName("data") val data: List<Menu>?,
    @SerializedName("size") val size: Int?
)