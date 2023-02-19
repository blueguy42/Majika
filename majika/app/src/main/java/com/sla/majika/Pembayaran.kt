package com.sla.majika

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import com.google.zxing.BarcodeFormat
import com.sla.majika.helper.PermissionCheck
import com.sla.majika.retrofit.RetrofitHelper
import com.sla.majika.retrofit.endpoint.EndpointPayment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.sla.majika.room.CartItemViewModel
import com.sla.majika.room.CartItemViewModelFactory

import com.sla.majika.fragment.header.HeaderPembayaran


class Pembayaran : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    
    private val cartItemViewModel: CartItemViewModel by viewModels {
        CartItemViewModelFactory((this?.application as MajikaApp).repository)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        replaceFragment(HeaderPembayaran())

        val intent = intent
        val total_harga = intent.extras!!.getString("total_harga")

        if (!PermissionCheck.isPermissionGranted(this, Manifest.permission.CAMERA)) {
            // only able to ask permission 2 times:
            // https://developer.android.com/training/permissions/requesting#handle-denial
            this.requestPermissions(arrayOf(Manifest.permission.CAMERA), PermissionCheck.getRequestCode())
            this.finish()
            return
        } else {
            val totalHarga: TextView = findViewById<TextView>(R.id.total_price)
            totalHarga.text = total_harga

            val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

            codeScanner = CodeScanner(this, scannerView)

            // Parameters (default values)
            codeScanner.camera = CodeScanner.CAMERA_BACK
            codeScanner.formats = listOf(BarcodeFormat.QR_CODE)
            codeScanner.autoFocusMode = AutoFocusMode.SAFE
            codeScanner.scanMode = ScanMode.SINGLE

            // Callbacks
            codeScanner.decodeCallback = DecodeCallback {
                runOnUiThread {
                    val endpointPaymentAPI =
                        RetrofitHelper.getInstance().create(EndpointPayment::class.java)
                    GlobalScope.launch {
                        val paymentResponse = endpointPaymentAPI.postPayment(it.text)
                        if (paymentResponse != null) {
                            var result = paymentResponse!!.body()!!.status
                            Log.d("PostData", result)

                            this@Pembayaran.runOnUiThread(Runnable {
                                val result1: TextView = findViewById<TextView>(R.id.scan_result)
                                val result2: TextView = findViewById<TextView>(R.id.scan_result2)
                                val result_icon: ImageView = findViewById<ImageView>(R.id.scan_result_icon)
                                if (result == "SUCCESS") {
                                    result1.text = "Berhasil"
                                    result2.text = "Sudah dibayar"
                                    result_icon.setImageResource(R.drawable.baseline_check_circle_24)
                                } else {
                                    result1.text = "Gagal"
                                    result2.text = "Belum dibayar"
                                    result_icon.setImageResource(R.drawable.baseline_x_circle_24)
                                }
                            })


                            if (result == "SUCCESS") {
                                Handler(Looper.getMainLooper()).postDelayed({
                                    cartItemViewModel.deleteAll()
                                    val intent = Intent(this@Pembayaran, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }, 5000)
                            } else {
                                codeScanner.startPreview()
                            }

                        }
                    }
                }
            }
            codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                runOnUiThread {
                    Toast.makeText(
                        this, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun replaceFragment(header: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.header_layout_pembayaran, header)
        fragmentTransaction.commit()
    }
}