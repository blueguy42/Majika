package com.sla.majika

import com.sla.majika.room.CartItem

interface CartItemClickListener
{
    fun add(item: CartItem)
    fun delete(item: CartItem)
    fun update(item: CartItem)
}