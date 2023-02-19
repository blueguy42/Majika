package com.sla.majika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sla.majika.room.CartItem

class MenuAdapter(private val menuList: ArrayList<CartItem>, val clickListener : CartItemClickListener) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View, clickListener: CartItemClickListener) : RecyclerView.ViewHolder(itemView){
        val nama : TextView = itemView.findViewById(R.id.nama_makanan)
        val harga : TextView = itemView.findViewById(R.id.harga_makanan)
        val terjual : TextView = itemView.findViewById(R.id.banyak_terjual)
        val deskripsi : TextView = itemView.findViewById(R.id.deskripsi_makanan)
        var quantity : TextView = itemView.findViewById(R.id.quantitas_menu)
        val add : Button = itemView.findViewById(R.id.add_menu)
        val dec : Button = itemView.findViewById(R.id.decrease_menu)
        val clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_menu, parent, false)
        return MenuViewHolder(itemView, clickListener)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]
        holder.nama.text = currentItem.nama
        holder.harga.text = currentItem.currency + ". " + currentItem.harga
        holder.terjual.text = "Terjual " + currentItem.terjual.toString()
        holder.deskripsi.text = currentItem.deskripsi
        holder.quantity.text = currentItem.quantity.toString()

        holder.add.setOnClickListener {
            if (currentItem.quantity == 0){
                holder.clickListener.add(CartItem(currentItem.nama,currentItem.harga.toInt(),1, currentItem.currency,currentItem.terjual,currentItem.deskripsi))
            }
            else {
                holder.clickListener.update(CartItem(currentItem.nama,currentItem.harga.toInt(),currentItem.quantity + 1,currentItem.currency, currentItem.terjual,currentItem.deskripsi))
            }
            menuList[position].quantity = menuList[position].quantity + 1
            notifyDataSetChanged()
        }

        if (currentItem.quantity == 0){
            holder.dec.setVisibility(View.GONE)
        } else{
            holder.dec.setVisibility(View.VISIBLE)
        }

        holder.dec.setOnClickListener{
            if (menuList[position].quantity == 1) {
                holder.clickListener.delete(CartItem(currentItem.nama,currentItem.harga.toInt(),1, currentItem.currency,currentItem.terjual,currentItem.deskripsi))
                menuList[position].quantity = menuList[position].quantity - 1
                notifyDataSetChanged()
            } else if (menuList[position].quantity > 1){
                holder.clickListener.update(CartItem(currentItem.nama,currentItem.harga.toInt(),currentItem.quantity - 1, currentItem.currency,currentItem.terjual,currentItem.deskripsi))
                menuList[position].quantity = menuList[position].quantity - 1
                notifyDataSetChanged()
            }
        }
    }


}
