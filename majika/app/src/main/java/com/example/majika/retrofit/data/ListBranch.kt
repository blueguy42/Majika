package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class ListBranch (
    @SerializedName("data") val data: List<Branch>?,
    @SerializedName("size") val size: Int?
)