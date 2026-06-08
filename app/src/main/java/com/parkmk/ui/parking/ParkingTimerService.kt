package com.parkmk.ui.parking

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.parkmk.R
import com.parkmk.ui.map.MainActivity

class ParkingTimerService : Service() {

    companion object {
        const val CHANNEL_ID     = "parking_timer_channel"
        const val NOTIF_ID       = 1001
        const val ACTION_START   = "START"
        const val ACTION_STOP    = "STOP"
        const val EXTRA_START_MS = "start_ms"
        const val EXTRA_SPOT     = "spot_name"
        const val EXTRA_RATE     = "rate"

        var isRunning  = false
        var startMs    = 0L
        var spotName   = ""
        var rate       = 0.0
    }

    private val handler = Handler(Looper.getMainLooper())

    private val ticker = object : Runnable {
        override fun run() {
            updateNotification()
            handler.postDelayed(this, 1000L)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                startMs   = intent.getLongExtra(EXTRA_START_MS, System.currentTimeMillis())
                spotName  = intent.getStringExtra(EXTRA_SPOT) ?: ""
                rate      = intent.getDoubleExtra(EXTRA_RATE, 0.0)
                isRunning = true

                createChannel()
                startForeground(NOTIF_ID, buildNotification())
                handler.post(ticker)

                ParkingNotificationManager.scheduleTestNotification(this, spotName)
            }
            ACTION_STOP -> {
                ParkingNotificationManager.cancelAll()
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun updateNotification() {
        val nm = getSystemService(NotificationManager::class.java)
        nm.notify(NOTIF_ID, buildNotification())
    }

    private fun buildNotification(): Notification {
        val elapsed = (System.currentTimeMillis() - startMs) / 1000L
        val h = elapsed / 3600
        val m = (elapsed % 3600) / 60
        val s = elapsed % 60
        val time = String.format("%02d:%02d:%02d", h, m, s)
        val cost = String.format("%.2f ден", elapsed / 60.0 * rate)

        val openIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_parking_pin)
            .setContentTitle("🅿 $spotName — $time")
            .setContentText(cost)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setSilent(true)
            .setContentIntent(openIntent)
            .build()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(
                CHANNEL_ID,
                "Активно паркирање",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setSound(null, null)
                enableVibration(false)
            }
            getSystemService(NotificationManager::class.java).createNotificationChannel(ch)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(ticker)
        ParkingNotificationManager.cancelAll()
        isRunning = false
        startMs = 0L
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}