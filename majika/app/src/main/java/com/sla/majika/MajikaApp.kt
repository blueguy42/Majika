package com.sla.majika

import android.app.Application
import com.sla.majika.room.CartItemRepository
import com.sla.majika.room.CartItemRoomDatabase

class MajikaApp : Application() {
    private val database by lazy {CartItemRoomDatabase.getDatabase(this)}
    val repository by lazy { CartItemRepository(database.CartItemDAO())}
}