package com.sla.majika.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDAO {

    @Query("SELECT * FROM cart_item_table")
    fun get(): List<CartItem>

    @Query("SELECT * FROM cart_item_table WHERE nama = :nama")
    fun getByNama(nama: String): CartItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartItem: CartItem)

    @Delete
    fun delete(cartItem: CartItem)

    @Update
    fun update(cartItem: CartItem)
}