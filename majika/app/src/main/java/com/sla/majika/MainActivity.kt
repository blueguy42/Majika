package com.sla.majika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sla.majika.databinding.ActivityMainBinding
import com.sla.majika.room.CartItemRepository
import com.sla.majika.room.CartItemRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Twibbon())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.camera -> replaceFragment(Twibbon())
                R.id.location -> replaceFragment(CabangRestoran())
                R.id.food -> replaceFragment(Menu())
                R.id.cart -> replaceFragment(Keranjang())
                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}