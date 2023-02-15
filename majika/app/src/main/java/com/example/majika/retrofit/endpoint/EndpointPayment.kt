package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.DataPayment
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface EndpointPayment {
    @POST("/v1/payment/{code}")
    suspend fun postPayment(@Path("code") code:String) : Response<DataPayment>
}