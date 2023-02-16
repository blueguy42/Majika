package com.sla.majika

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sla.majika.helper.PermissionCheck
import java.io.File
import java.io.FileOutputStream
import java.util.*

// Based on https://youtu.be/S-7H72UTiBU
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Twibbon : Fragment(), TextureView.SurfaceTextureListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var capReq: CaptureRequest.Builder
    lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread
    private lateinit var cameraManager: CameraManager
    lateinit var textureView: TextureView
    lateinit var cameraCaptureSession: CameraCaptureSession
    lateinit var cameraDevice: CameraDevice
    lateinit var imageReader: ImageReader
    private lateinit var warningText: TextView
    private lateinit var capturePhotoButton: Button
    private lateinit var switchCameraButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_twibbon, container, false)

        warningText = view.findViewById(R.id.warningCameraTwibbon)
        warningText.visibility = View.INVISIBLE
        capturePhotoButton = view.findViewById(R.id.btnCapture)
        capturePhotoButton.visibility = View.VISIBLE
        switchCameraButton = view.findViewById(R.id.btnSwitch)
        switchCameraButton.visibility = View.VISIBLE

        textureView = view.findViewById(R.id.textureViewTwibbon)
        cameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        if (!PermissionCheck.isPermissionGranted(requireActivity() as AppCompatActivity, Manifest.permission.CAMERA)) {
            warningText.visibility = View.VISIBLE
            warningText.text = resources.getString(R.string.twibbon_warning_no_permissions)
            capturePhotoButton.visibility = View.GONE
            switchCameraButton.visibility = View.GONE
            
            // only able to ask permission 2 times:
            // https://developer.android.com/training/permissions/requesting#handle-denial
            this.requestPermissions(arrayOf(Manifest.permission.CAMERA), PermissionCheck.getRequestCode())
            return view
        } else {

            textureView.surfaceTextureListener = this

            imageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1)
            imageReader.setOnImageAvailableListener({ p0 ->
                val image = p0?.acquireLatestImage()
                val buffer = image!!.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)

                val file = File(activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    Calendar.getInstance().time.toString() + "image.jpeg")
                val opStream = FileOutputStream(file)

                opStream.write(bytes)

                opStream.close()
                image.close()

                Toast.makeText(activity as AppCompatActivity, "Image captured", Toast.LENGTH_SHORT).show()
            }, handler)

            view.findViewById<Button>(R.id.btnCapture).setOnClickListener {
                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                capReq.addTarget(imageReader.surface)
                cameraCaptureSession.capture(capReq.build(), null, null)
            }
            return view
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::cameraDevice.isInitialized) { cameraDevice.close() }

        handler.removeCallbacksAndMessages(null)
        handlerThread.quitSafely()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        PermissionCheck.handlePermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        cameraManager.openCamera(cameraManager.cameraIdList[0], object: CameraDevice.StateCallback(){
            override fun onOpened(p0: CameraDevice) {
                cameraDevice = p0
                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                val surface = Surface(textureView.surfaceTexture)
                capReq.addTarget(surface)

                cameraDevice.createCaptureSession(listOf(surface, imageReader.surface), object: CameraCaptureSession.StateCallback(){
                    override fun onConfigureFailed(p0: CameraCaptureSession) {

                    }

                    override fun onConfigured(p0: CameraCaptureSession) {
                        cameraCaptureSession = p0
                        cameraCaptureSession.setRepeatingRequest(capReq.build(), null, null)
                    }
                }, handler)
            }

            override fun onDisconnected(p0: CameraDevice) {

            }

            override fun onError(p0: CameraDevice, p1: Int) {

            }
        }, handler)
    }

    override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
        openCamera()
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
        return false
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
    }
}