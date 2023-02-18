package com.sla.majika

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sla.majika.databinding.ActivityMainBinding
import com.sla.majika.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Keranjang.newInstance] factory method to
 * create an instance of this fragment.
 */
class Keranjang : Fragment(), CartItemClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    lateinit var cartItemList: ArrayList<CartItem>
    lateinit var adapter: CartItemsAdapter
    private val cartItemViewModel: CartItemViewModel by viewModels {
        CartItemViewModelFactory((activity?.application as MajikaApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_keranjang, container, false)
        val btnPembayaran = view.findViewById<Button>(R.id.buttonPembayaran)
        btnPembayaran.setOnClickListener {
            val intent = Intent(activity, Pembayaran::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun add(cartItem: CartItem){
        cartItemViewModel.insert(cartItem)

    }

    override fun delete(cartItem: CartItem){
        cartItemViewModel.delete(cartItem)
    }

    override fun update(cartItem: CartItem){
        cartItemViewModel.update(cartItem)
    }

    fun deleteAll(){
        cartItemViewModel.deleteAll()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartItemList = arrayListOf<CartItem>()

        var layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.Daftar_Cart)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CartItemsAdapter(cartItemList,this)
        recyclerView.adapter = adapter

        cartItemViewModel.allItems.observe(viewLifecycleOwner){
            adapter = CartItemsAdapter(ArrayList(it), this)
            recyclerView.adapter = adapter
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Keranjang.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Keranjang().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}