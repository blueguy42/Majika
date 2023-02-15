package com.example.majika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val menuList: ArrayList<MenuModel>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nama : TextView = itemView.findViewById(R.id.nama_makanan)
        val harga : TextView = itemView.findViewById(R.id.harga_makanan)
        val terjual : TextView = itemView.findViewById(R.id.banyak_terjual)
        val deskripsi : TextView = itemView.findViewById(R.id.deskripsi_makanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_menu, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]
        holder.nama.text = currentItem.nama
        holder.harga.text = currentItem.harga
        holder.terjual.text = currentItem.terjual
        holder.deskripsi.text = currentItem.deskripsi

    }


}