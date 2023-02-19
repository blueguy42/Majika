package com.sla.majika.fragment.header

import android.app.Activity
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sla.majika.R


class HeaderMenu : Fragment(), SensorEventListener {
    private lateinit var tempLabel: TextView
    private lateinit var mSensorManager: SensorManager
    private var mTemperature: Sensor? = null
    private lateinit var mActivity: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_header_menu, container, false)
        tempLabel = view.findViewById(R.id.tempLabel)
        mSensorManager = mActivity.getSystemService(SENSOR_SERVICE) as SensorManager
        mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (mTemperature == null) {
            tempLabel.text = ""
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context
        }
    }


    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent) {
        val ambientTemperature: Float = p0.values[0]
        tempLabel.text = "${String.format("%.1f", ambientTemperature)}°C"
    }

    override fun onAccuracyChanged(p0: Sensor, p1: Int) {}
}