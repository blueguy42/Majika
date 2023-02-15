package com.example.majika

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.retrofit.RetrofitHelper
import com.example.majika.retrofit.endpoint.EndpointMenu
import com.example.majika.retrofit.endpoint.EndpointMenuDrink
import com.example.majika.retrofit.endpoint.EndpointMenuFood
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class Menu : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var recyclerView: RecyclerView
    lateinit var modelList: ArrayList<MenuModel>
    lateinit var adapter: MenuAdapter

//    lateinit var nama: Array<String>
//    lateinit var harga: Array<String>
//    lateinit var terjual: Array<String>
//    lateinit var deskripsi: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val menuAPI = RetrofitHelper.getInstance().create(EndpointMenu::class.java)
        val menuDrinkAPI = RetrofitHelper.getInstance().create(EndpointMenuDrink::class.java)
        val menuFoodAPI = RetrofitHelper.getInstance().create(EndpointMenuFood::class.java)
        GlobalScope.launch {
            val menuData = menuAPI.getMenu()
            if (menuData != null) {
                // Checking the results
                Log.d("GetData", menuData.body().toString())
            }
            val menuDrinkData = menuDrinkAPI.getMenuDrink()
            if (menuDrinkData != null) {
                // Checking the results
                Log.d("GetData", menuDrinkData.body().toString())
            }
            val menuFoodData = menuFoodAPI.getMenuFood()
            if (menuFoodData != null) {
                // Checking the results
                Log.d("GetData", menuFoodData.body().toString())
            }
        }
//        val layoutManager = LinearLayoutManager(context)
//        recyclerView = view.findViewById(R.id.Daftar_Makanan)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.setHasFixedSize(true)
//        adapter = MenuAdapter(modelList)
//        recyclerView.adapter = adapter
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