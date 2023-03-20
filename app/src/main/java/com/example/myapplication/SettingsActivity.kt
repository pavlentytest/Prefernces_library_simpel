package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import org.w3c.dom.Text

class SettingsActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var pressureSensor: Sensor? = null
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sharedPref =  getPreferences(Context.MODE_PRIVATE) ?: return
    }

    override fun onResume() {
        super.onResume()
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressureSensor != null) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_GAME)
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == pressureSensor!!.type) {
            with (sharedPref.edit()) {
                putFloat("pressure", event.values[0])
                apply()
            }
        }
        val getValue = sharedPref.getFloat("pressure",0f)
        Log.d("RRR", getValue.toString())
        findViewById<TextView>(R.id.textView).setText(getValue.toString())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}