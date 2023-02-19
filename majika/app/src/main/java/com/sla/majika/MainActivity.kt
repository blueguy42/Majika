package com.sla.majika

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.sla.majika.databinding.ActivityMainBinding
import com.sla.majika.fragment.header.HeaderCabang
import com.sla.majika.fragment.header.HeaderKeranjang
import com.sla.majika.fragment.header.HeaderTwibbon

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Twibbon(), HeaderTwibbon())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.camera -> replaceFragment(Twibbon(), HeaderTwibbon())
                R.id.location -> replaceFragment(CabangRestoran(), HeaderCabang())
                R.id.food -> replaceFragment(Menu(), HeaderTwibbon())
                R.id.cart -> replaceFragment(Keranjang(), HeaderKeranjang())
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