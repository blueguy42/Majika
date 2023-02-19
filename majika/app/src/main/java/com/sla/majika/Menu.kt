package com.sla.majika

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.sla.majika.databinding.ActivityMainBinding
import com.sla.majika.retrofit.RetrofitHelper
import com.sla.majika.retrofit.endpoint.EndpointMenuDrink
import com.sla.majika.retrofit.endpoint.EndpointMenuFood
import com.sla.majika.room.CartItem
import com.sla.majika.room.CartItemViewModel
import com.sla.majika.room.CartItemViewModelFactory
import kotlinx.coroutines.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class Menu : Fragment(), CartItemClickListener {
    private lateinit var mContext: Context
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerView2: RecyclerView
    lateinit var modelList: ArrayList<CartItem>
    lateinit var tempModelList: ArrayList<CartItem>
    lateinit var modelList2: ArrayList<CartItem>
    lateinit var tempModelList2: ArrayList<CartItem>
    lateinit var adapter: MenuAdapter
    lateinit var adapter2: MenuAdapter
    lateinit var kuantitasArr: List<CartItem>
    lateinit var text_input: TextInputEditText

    private val cartItemViewModel: CartItemViewModel by viewModels {
        CartItemViewModelFactory((activity?.application as MajikaApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        kuantitasArr = cartItemViewModel.getAll()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        modelList = arrayListOf<CartItem>()
        tempModelList = arrayListOf<CartItem>()
        modelList2 = arrayListOf<CartItem>()
        tempModelList2 = arrayListOf<CartItem>()
        val fragmentcontext = this

        val menuDrinkAPI = RetrofitHelper.getInstance().create(EndpointMenuDrink::class.java)
        val menuFoodAPI = RetrofitHelper.getInstance().create(EndpointMenuFood::class.java)
        lifecycleScope.launch {
        val operation = GlobalScope.async(Dispatchers.Default) {
            try {
                val menuFoodData = menuFoodAPI.getMenuFood()
                if (menuFoodData != null) {
                    // Checking the result
                    val datamakanan = menuFoodData!!.body()!!.data
                    for (i in datamakanan){
                        val name = i.name
                        val price = i.price.toInt()
                        val sold = i.sold.toInt()
                        val desc = i.description
                        val currency = i.currency
                        val quantity = getQuantityByNama(name,price,currency,sold,desc)
                        modelList.add(CartItem(name,price,quantity,currency,sold,desc))
                    }
                    tempModelList.addAll(modelList)
                }

                val menuDrinkData = menuDrinkAPI.getMenuDrink()
                if (menuDrinkData != null) {
                    // Checking the result
                    val dataminuman = menuDrinkData!!.body()!!.data
                    for (i in dataminuman){
                        val name = i.name
                        val price = i.price.toInt()
                        val sold = i.sold.toInt()
                        val desc = i.description
                        val currency = i.currency
                        val quantity = getQuantityByNama(name,price,currency,sold,desc)
                        modelList2.add(CartItem(name,price,quantity,currency,sold,desc))
                    }
                    tempModelList2.addAll(modelList2)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {Toast.makeText(mContext, "Connection timed out", Toast.LENGTH_SHORT).show()}
            }
        }
            operation.await()

            val layoutManager = LinearLayoutManager(context)
            recyclerView = view.findViewById(R.id.Daftar_Makanan)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            adapter = MenuAdapter(tempModelList, fragmentcontext)
            recyclerView.adapter = adapter

            val layoutManager2 = LinearLayoutManager(context)
            recyclerView2 = view.findViewById(R.id.Daftar_Minuman)
            recyclerView2.layoutManager = layoutManager2
            recyclerView2.setHasFixedSize(true)
            adapter2 = MenuAdapter(tempModelList2,fragmentcontext)
            recyclerView2.adapter = adapter2

            recyclerView.setNestedScrollingEnabled(false);
            recyclerView2.setNestedScrollingEnabled(false);
            text_input = view.findViewById(R.id.text_input)

//            SEARCH
            text_input.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    var newText = s.toString()
                    tempModelList.clear()
                    tempModelList2.clear()
                    val searchText = newText!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()){
                        for (i in modelList.indices){
                            if (modelList[i].nama.lowercase(Locale.getDefault()).contains(searchText)){
                                tempModelList.add(modelList[i])
                            }
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }
                        for (i in modelList2.indices){
                            if (modelList2[i].nama.lowercase(Locale.getDefault()).contains(searchText)){
                                tempModelList2.add(modelList2[i])
                            }
                            recyclerView2.adapter!!.notifyDataSetChanged()
                        }
                    }else{
                        tempModelList.clear()
                        tempModelList.addAll(modelList)
                        tempModelList2.clear()
                        tempModelList2.addAll(modelList2)
                        recyclerView.adapter!!.notifyDataSetChanged()
                        recyclerView2.adapter!!.notifyDataSetChanged()
                    }
                }
            })
        }

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

    fun getQuantityByNama(nama: String, harga: Int, currency: String, terjual: Int, deskripsi: String): Int{
        for (i in kuantitasArr){
            if (i.nama == nama && i.harga == harga && i.currency == currency && i.terjual == terjual && i.deskripsi == deskripsi){
                return i.quantity
            }
        }
        return 0
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Menu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Menu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}