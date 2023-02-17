package com.sla.majika.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDAO {

    @Query("SELECT * FROM cart_item_table")
    fun getItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartItem: CartItem)

    @Query("DELETE FROM cart_item_table")
    fun deleteAll()
}