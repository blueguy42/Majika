package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class DataListBranch (
    @SerializedName("data") val data: List<DataBranch>?,
    @SerializedName("size") val size: Int?
)