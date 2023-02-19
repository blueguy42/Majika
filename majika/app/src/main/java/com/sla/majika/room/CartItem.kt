package com.sla.majika.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item_table", primaryKeys = ["nama", "harga", "currency", "terjual", "deskripsi"])
data class CartItem(
    @ColumnInfo(name = "nama") val nama : String,
    val harga : Int,
    var quantity : Int,
    var currency : String,
    var terjual : Int,
    var deskripsi : String,
)
