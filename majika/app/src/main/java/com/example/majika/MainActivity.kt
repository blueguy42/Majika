package com.example.majika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.majika.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Twibbon())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.camera -> replaceFragment(Twibbon())
                R.id.location -> replaceFragment(CabangRestoran())
                R.id.food -> replaceFragment(Menu())
                R.id.cart -> replaceFragment(Keranjang())
                else ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}