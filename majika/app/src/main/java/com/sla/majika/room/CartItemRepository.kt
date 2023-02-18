package com.sla.majika.room

import androidx.annotation.WorkerThread
import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class CartItemRepository(private val dao: CartItemDAO) {

    val allCartItems: Flow<List<CartItem>> = dao.get()

    fun getByNama(nama:String): CartItem{
        return dao.getByNama(nama)
    }

    fun getHargaTotal(): List<CurrencyPrice> {
        return dao.getHargaTotal()
    }

    fun getAll(): List<CartItem> {
        return dao.getAll()
    }

    fun insert(cartItem: CartItem){
        dao.insert(cartItem)
    }

    fun delete(cartItem: CartItem){
        dao.delete(cartItem)
    }

    fun update(cartItem: CartItem){
        dao.update(cartItem)
    }



    fun deleteAll(){
        dao.deleteAll()
    }
}