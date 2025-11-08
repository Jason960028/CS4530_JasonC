package com.example.a5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.a5.ui.theme.CS4530_JasonCTheme

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private lateinit var viewModel: MarbleViewModel
    private var lastUpdateTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[MarbleViewModel::class.java]

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensor = gravitySensor ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContent {
            CS4530_JasonCTheme {
                MarbleScreen(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // register sensor listener
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        lastUpdateTime = System.currentTimeMillis()
    }

    override fun onPause() {
        super.onPause()
        // unregister sensor listener
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val currentTime = System.currentTimeMillis()
            val deltaTime = (currentTime - lastUpdateTime) / 1000f
            lastUpdateTime = currentTime

            // Invert sensor values to match expected marble movement direction
            // Tilt right → positive gravity → marble moves right
            // Tilt left → negative gravity → marble moves left
            val gravityX = -it.values[0]
            val gravityY = it.values[1]

            viewModel.updateFromSensor(gravityX, gravityY, deltaTime)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}