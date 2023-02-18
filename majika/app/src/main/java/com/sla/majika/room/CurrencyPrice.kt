package com.sla.majika.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class CurrencyPrice(
    val currency : String,
    val totalharga : Int,
)
