package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.Payment
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface Payment {
    @POST("/v1/payment/{code}")
    suspend fun postPayment(@Path("code") code:String) : Response<Payment>
}