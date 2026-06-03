package com.parkmk

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class ParkMKApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "parkmk_channel",
                "ParkMK Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "ParkMK parking notifications"
                enableLights(true)
                enableVibration(true)
            }
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }
}
