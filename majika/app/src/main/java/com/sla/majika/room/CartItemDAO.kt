package com.sla.majika.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.Currency

@Dao
interface CartItemDAO {

    @Query("SELECT * FROM cart_item_table")
    fun get(): Flow<List<CartItem>>

    @Query("SELECT currency, sum(quantity*harga) AS totalharga FROM cart_item_table GROUP BY currency")
    fun getHargaTotal(): List<CurrencyPrice>

    @Query("SELECT * FROM cart_item_table")
    fun getAll(): List<CartItem>

    @Query("SELECT * FROM cart_item_table WHERE nama = :nama")
    fun getByNama(nama: String): CartItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartItem: CartItem)

    @Delete
    fun delete(cartItem: CartItem)

    @Update
    fun update(cartItem: CartItem)

    @Query("DELETE FROM cart_item_table")
    fun deleteAll()
}