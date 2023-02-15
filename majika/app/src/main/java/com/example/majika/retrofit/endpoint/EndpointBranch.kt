package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.DataListBranch
import retrofit2.Response
import retrofit2.http.GET

interface EndpointBranch {
    @GET("/v1/branch")
    suspend fun getBranch() : Response<DataListBranch>
}