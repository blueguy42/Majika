package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.DataListMenu
import retrofit2.Response
import retrofit2.http.GET

interface EndpointMenuDrink {
    @GET("/v1/menu/drink")
    suspend fun getMenuDrink() : Response<DataListMenu>
}