package com.sla.majika.retrofit.endpoint

import com.sla.majika.retrofit.data.DataListMenu
import retrofit2.Response
import retrofit2.http.GET

interface EndpointMenuFood {
    @GET("/v1/menu/food")
    suspend fun getMenuFood() : Response<DataListMenu>
}