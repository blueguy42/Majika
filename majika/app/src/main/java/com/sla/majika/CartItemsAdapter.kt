package com.sla.majika

import com.sla.majika.room.CartItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartItemsAdapter(private val cartItemList: ArrayList<CartItem>) :
    RecyclerView.Adapter<CartItemsAdapter.CartItemViewHolder>() {

    class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nama : TextView = itemView.findViewById(R.id.nama_cart)
        val harga : TextView = itemView.findViewById(R.id.harga_cart)
        var quantity : TextView = itemView.findViewById(R.id.quantitas_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_cartitem, parent, false)
        return CartItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val currentItem = cartItemList[position]
        holder.nama.text = currentItem.nama
        holder.harga.text = currentItem.harga.toString()
        holder.quantity.text = currentItem.quantity.toString()
    }


}
