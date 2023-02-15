package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.ListMenu
import retrofit2.Response
import retrofit2.http.GET

interface Menu {
    @GET("/v1/menu")
    suspend fun getMenu() : Response<ListMenu>
}