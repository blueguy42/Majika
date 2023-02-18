package com.sla.majika

import com.sla.majika.room.CartItem

interface CartItemClickListener
{
    fun add(item: CartItem)
    fun remove(item: CartItem)
}