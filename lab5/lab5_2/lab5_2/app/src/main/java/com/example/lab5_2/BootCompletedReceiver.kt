package com.example.lab5_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Просто повідомлення в лог і Toast (Toast може бути не показаний, залежить від системи)
            Log.d("BootCompletedReceiver", "Пристрій повністю завантажений (BOOT_COMPLETED)")
            Toast.makeText(context, "Пристрій завантажено", Toast.LENGTH_LONG).show()
        }
    }
}
