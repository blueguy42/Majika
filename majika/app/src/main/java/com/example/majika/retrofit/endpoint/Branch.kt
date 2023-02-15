package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.ListBranch
import retrofit2.Response
import retrofit2.http.GET

interface Branch {
    @GET("/v1/branch")
    suspend fun getBranch() : Response<ListBranch>
}