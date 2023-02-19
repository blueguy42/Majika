package com.sla.majika.fragment.header

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.sla.majika.MainActivity
import com.sla.majika.Pembayaran
import com.sla.majika.R

class HeaderPembayaran : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_header_pembayaran, container, false)
        val btnBack = view.findViewById<Button>(R.id.button_back)
        btnBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("origin", "pembayaran")
            startActivity(intent)
        }
        return view
    }
}