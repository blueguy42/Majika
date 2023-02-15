package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.ListMenu
import retrofit2.Response
import retrofit2.http.GET

interface MenuDrink {
    @GET("/v1/menu/drink")
    suspend fun getMenuDrink() : Response<ListMenu>
}