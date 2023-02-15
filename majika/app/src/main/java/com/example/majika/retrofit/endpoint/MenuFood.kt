package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.ListMenu
import retrofit2.Response
import retrofit2.http.GET

interface MenuFood {
    @GET("/v1/menu/food")
    suspend fun getMenuFood() : Response<ListMenu>
}