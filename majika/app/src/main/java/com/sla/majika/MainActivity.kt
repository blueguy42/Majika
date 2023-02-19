package com.sla.majika

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sla.majika.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Menu())
        binding.bottomNavigationView.selectedItemId = R.id.food

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