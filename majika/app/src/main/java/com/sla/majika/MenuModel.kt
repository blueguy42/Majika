package com.sla.majika

data class MenuModel(
    val nama:String,
    val harga:Int,
    val terjual:String,
    val deskripsi:String,
    var quantity: Int,
    var currency: String)
