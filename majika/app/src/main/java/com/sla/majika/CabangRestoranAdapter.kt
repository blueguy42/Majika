package com.sla.majika

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class CabangRestoranAdapter(private val cabangList: ArrayList<CabangRestoranModel>) :
    RecyclerView.Adapter<CabangRestoranAdapter.CabangRestoranViewHolder>() {

    class CabangRestoranViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.name_branch)
        // val popular_food : TextView = itemView.findViewById(R.id.popular_food_branch)
        val address: TextView = itemView.findViewById(R.id.address_branch)
        // val contact_person : TextView = itemView.findViewById(R.id.contact_person_branch)
        val phoneNumber : TextView = itemView.findViewById(R.id.phone_number_branch)
        val buttonMaps : Button = itemView.findViewById(R.id.button_maps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabangRestoranViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_cabang, parent, false)
        return CabangRestoranViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cabangList.size
    }

    override fun onBindViewHolder(holder: CabangRestoranViewHolder, position: Int) {
        val currentItem = cabangList[position]
        holder.name.text = currentItem.name
//        holder.popular_food.text = currentItem.popular_food
        holder.address.text = currentItem.address
//        holder.contact_person.text = currentItem.contact_person
        holder.phoneNumber.text = currentItem.phone_number
        holder.buttonMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:${currentItem.latitude},${currentItem.longitude}>?q=${currentItem.latitude},${currentItem.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(holder.itemView.context, mapIntent, null)
        }
    }


}
