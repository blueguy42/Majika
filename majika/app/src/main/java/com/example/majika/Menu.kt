package com.example.majika

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.retrofit.RetrofitHelper
import com.example.majika.retrofit.endpoint.EndpointMenu
import com.example.majika.retrofit.endpoint.EndpointMenuDrink
import com.example.majika.retrofit.endpoint.EndpointMenuFood
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

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
    lateinit var tempModelList: ArrayList<MenuModel>
    lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_box, menu);
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempModelList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    for (i in modelList.indices){
                        if (modelList[i].nama.lowercase(Locale.getDefault()).contains(searchText)){
                            tempModelList.add(modelList[i])
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }else{
                    tempModelList.clear()
                    tempModelList.addAll(modelList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)
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
        modelList = arrayListOf<MenuModel>()
        tempModelList = arrayListOf<MenuModel>()
        val menuAPI = RetrofitHelper.getInstance().create(EndpointMenu::class.java)
        lifecycleScope.launch {
        val operation = GlobalScope.async(Dispatchers.Default) {
            val menuData = menuAPI.getMenu()
            if (menuData != null) {
                // Checking the result
                Log.d("GetData", menuData.body().toString())
                val datamakanan = menuData!!.body()!!.data
                for (i in datamakanan){
                    val name = i.name
                    val price = i.currency + ". " + i.price.toString()
                    val sold = "Terjual " + i.sold.toString()
                    val desc = i.description
                    val quantity = 0
                    modelList.add(MenuModel(name,price,sold,desc,0))
                }
                tempModelList.addAll(modelList)
            }
        }
            operation.await()
            val layoutManager = LinearLayoutManager(context)
            recyclerView = view.findViewById(R.id.Daftar_Makanan)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            adapter = MenuAdapter(tempModelList)
            recyclerView.adapter = adapter
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

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