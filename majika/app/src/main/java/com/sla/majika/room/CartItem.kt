package com.sla.majika.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item_table")
data class CartItem(
    @PrimaryKey @ColumnInfo(name = "nama") val nama : String,
    val harga : Int,
    val quantity : Int
)
