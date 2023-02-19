package com.sla.majika

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sla.majika.databinding.ActivityMainBinding
import com.sla.majika.fragment.header.HeaderCabang
import com.sla.majika.fragment.header.HeaderKeranjang
import com.sla.majika.fragment.header.HeaderMenu
import com.sla.majika.fragment.header.HeaderTwibbon

var menuId: Int = 0

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val origin = intent.getStringExtra("origin")
        when (origin) {
            "splash" -> {
                replaceFragment(Menu(), HeaderMenu())
                binding.bottomNavigationView.selectedItemId = R.id.food
            }
            "pembayaran" -> {
                replaceFragment(Keranjang(), HeaderKeranjang())
                binding.bottomNavigationView.selectedItemId = R.id.cart
            }
            else -> {
                when (menuId) {
                    0 -> {
                        binding.bottomNavigationView.selectedItemId = R.id.camera
                        replaceFragment(Twibbon(), HeaderTwibbon())
                    }
                    1 -> {
                        binding.bottomNavigationView.selectedItemId = R.id.location
                        replaceFragment(CabangRestoran(), HeaderCabang())
                    }
                    2 -> {
                        binding.bottomNavigationView.selectedItemId = R.id.food
                        replaceFragment(Menu(), HeaderMenu())
                    }
                    3 -> {
                        binding.bottomNavigationView.selectedItemId = R.id.cart
                        replaceFragment(Keranjang(), HeaderKeranjang())
                    }
                    else -> {
                    }
                }
            }
        }

        intent.removeExtra("origin")

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.camera -> {
                    menuId = 0
                    replaceFragment(Twibbon(), HeaderTwibbon())
                }
                R.id.location -> {
                    menuId = 1
                    replaceFragment(CabangRestoran(), HeaderCabang())
                }
                R.id.food -> {
                    menuId = 2
                    replaceFragment(Menu(), HeaderMenu())
                }
                R.id.cart -> {
                    menuId = 3
                    replaceFragment(Keranjang(), HeaderKeranjang())
                }
                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, header: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.replace(R.id.header_layout, header)
        fragmentTransaction.commit()
    }
}