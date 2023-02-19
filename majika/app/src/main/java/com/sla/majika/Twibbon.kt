package com.sla.majika

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.sla.majika.helper.PermissionCheck


// Based on https://youtu.be/S-7H72UTiBU
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Twibbon : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var imagePreview: Boolean = false

    private lateinit var mContext: Context

    private lateinit var previewView: PreviewView
    private lateinit var previewImage: ImageView
    private lateinit var twibbon: ImageView
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var warningText: TextView
    private lateinit var capturePhotoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        cameraProviderFuture = ProcessCameraProvider.getInstance(mContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_twibbon, container, false)

        previewView = view.findViewById(R.id.previewView)
        previewView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE
        previewView.scaleType = PreviewView.ScaleType.FILL_CENTER
        previewView.visibility = View.VISIBLE
        previewImage = view.findViewById(R.id.imagePreview)
        twibbon = view.findViewById(R.id.twibbon)
        twibbon.visibility = View.VISIBLE

        warningText = view.findViewById(R.id.warningCameraTwibbon)
        warningText.visibility = View.GONE
        capturePhotoButton = view.findViewById(R.id.btnCapture)
        capturePhotoButton.visibility = View.VISIBLE

        if (imagePreview) {
            previewView.visibility = View.GONE
            previewImage.visibility = View.VISIBLE
            capturePhotoButton.text = resources.getString(R.string.btn_take_photo)
        } else {
            previewView.visibility = View.VISIBLE
            previewImage.visibility = View.GONE
            capturePhotoButton.text = resources.getString(R.string.btn_capture)
        }

        if (!PermissionCheck.isPermissionGranted(requireActivity() as AppCompatActivity,Manifest.permission.CAMERA)) {
            warningText.visibility = View.VISIBLE
            warningText.text = resources.getString(R.string.twibbon_warning_no_permissions)
            capturePhotoButton.visibility = View.GONE
            previewView.visibility = View.GONE
            twibbon.visibility = View.GONE

            // only able to ask permission 2 times:
            // https://developer.android.com/training/permissions/requesting#handle-denial
            this.requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                PermissionCheck.getRequestCode()
            )
            return view
        }

        capturePhotoButton.setOnClickListener {
            if (imagePreview) {
                previewView.visibility = View.VISIBLE
                previewImage.visibility = View.GONE
                capturePhotoButton.text = resources.getString(R.string.btn_capture)
                imagePreview = false

            } else {
                previewView.visibility = View.GONE
                previewImage.visibility = View.VISIBLE
                capturePhotoButton.text = resources.getString(R.string.btn_take_photo)
                imagePreview = true
                val bitmap = previewView.bitmap
                previewImage.setImageBitmap(bitmap)
                Toast.makeText(mContext, "Image captured", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    // override onStart
    override fun onStart() {
        super.onStart()
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(mContext))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProviderFuture.get().unbindAll()
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        preview.setSurfaceProvider(previewView.surfaceProvider)

        cameraProvider.bindToLifecycle(mContext as LifecycleOwner, cameraSelector, preview)
    }
}
