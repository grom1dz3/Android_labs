package com.example.lab5_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var airplaneModeTextView: TextView

    private val powerConnectedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_POWER_CONNECTED) {
                Toast.makeText(context, "Зарядний пристрій підключено", Toast.LENGTH_SHORT).show()
                Log.d("PowerConnectedReceiver", "Зарядка підключена")
            }
        }
    }
    private val airplaneReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val isEnabled = android.provider.Settings.Global.getInt(
                context.contentResolver,
                android.provider.Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0
            airplaneModeTextView.text = if (isEnabled) "Авіарежим УВІМКНЕНО" else "Авіарежим ВИМКНЕНО"
        }
    }


   /* private val airplaneModeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                val isAirplaneModeOn = isAirplaneModeOn(context!!)
                airplaneModeTextView.text = if (isAirplaneModeOn) "Авіарежим увімкнено" else "Авіарежим вимкнено"
                Log.d("AirplaneModeReceiver", "Авіарежим змінено: $isAirplaneModeOn")
            }
        }
    } */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        airplaneModeTextView = findViewById(R.id.airplane_mode_status)
        // Встановлюємо початковий стан авіарежиму при запуску
        airplaneModeTextView.text = if (isAirplaneModeOn(this)) "Авіарежим увімкнено" else "Авіарежим вимкнено"
    }

    override fun onStart() {
        super.onStart()
        // Реєструємо динамічні приймачі
        registerReceiver(powerConnectedReceiver, IntentFilter(Intent.ACTION_POWER_CONNECTED))
        registerReceiver(airplaneReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        // Скасовуємо реєстрацію приймачів
        unregisterReceiver(powerConnectedReceiver)
        unregisterReceiver(airplaneReceiver)
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }
}
