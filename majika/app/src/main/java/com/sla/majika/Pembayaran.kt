package com.sla.majika

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.sla.majika.helper.PermissionCheck
import com.sla.majika.retrofit.RetrofitHelper
import com.sla.majika.retrofit.endpoint.EndpointPayment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Pembayaran : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        if (!PermissionCheck.isPermissionGranted(this, Manifest.permission.CAMERA)) {
            // only able to ask permission 2 times:
            // https://developer.android.com/training/permissions/requesting#handle-denial
            this.requestPermissions(arrayOf(Manifest.permission.CAMERA), PermissionCheck.getRequestCode())
            this.finish()
            return
        } else {
            val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

            codeScanner = CodeScanner(this, scannerView)

            // Parameters (default values)
            codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW

            // Callbacks
            codeScanner.decodeCallback = DecodeCallback {
                runOnUiThread {
                    Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()

                    val textView: TextView = findViewById<TextView>(R.id.scan_result)
                    textView.text = it.text

                    val endpointPaymentAPI =
                        RetrofitHelper.getInstance().create(EndpointPayment::class.java)
                    GlobalScope.launch {
                        val paymentResponse = endpointPaymentAPI.postPayment(it.text)
                        if (paymentResponse != null) {
                            // Checking the results
                            Log.d("PostData", paymentResponse.body().toString())
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
}