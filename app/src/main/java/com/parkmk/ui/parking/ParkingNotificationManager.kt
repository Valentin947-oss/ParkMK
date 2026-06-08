package com.parkmk.ui.parking

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.parkmk.R
import com.parkmk.ui.map.MainActivity

object ParkingNotificationManager {

    private val handler = Handler(Looper.getMainLooper())
    private var notif1Runnable: Runnable? = null
    private var notif2Runnable: Runnable? = null

    // Тест — 10 секунди
    fun scheduleTestNotification(context: Context, spotName: String) {
        cancelAll()
        val notifManager = context.getSystemService(NotificationManager::class.java)
        notif1Runnable = Runnable {
            showNotification(
                context, notifManager,
                id    = 2001,
                title = "⚠️ Паркирањето истекува!",
                body  = "Тест нотификација за $spotName"
            )
        }
        handler.postDelayed(notif1Runnable!!, 10_000L)
    }

    // Вистински — 1 час и 10 мин пред истек
    fun scheduleNotifications(context: Context, spotName: String, durationSeconds: Long) {
        cancelAll()
        val notifManager = context.getSystemService(NotificationManager::class.java)

        val oneHourBefore = (durationSeconds - 3600) * 1000L
        if (oneHourBefore > 0) {
            notif1Runnable = Runnable {
                showNotification(
                    context, notifManager, 2001,
                    "⏰ Паркирањето истекува за 1 час",
                    "Паркирањето на $spotName истекува за 1 час"
                )
            }
            handler.postDelayed(notif1Runnable!!, oneHourBefore)
        }

        val tenMinBefore = (durationSeconds - 600) * 1000L
        if (tenMinBefore > 0) {
            notif2Runnable = Runnable {
                showNotification(
                    context, notifManager, 2002,
                    "⚠️ Паркирањето истекува за 10 минути!",
                    "Паркирањето на $spotName истекува наскоро!"
                )
            }
            handler.postDelayed(notif2Runnable!!, tenMinBefore)
        }
    }

    private fun showNotification(
        context: Context,
        manager: NotificationManager,
        id: Int,
        title: String,
        body: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pending = PendingIntent.getActivity(
            context, id, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val notif = NotificationCompat.Builder(context, "parkmk_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSilent(false)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pending)
            .build()
        manager.notify(id, notif)
    }

    fun cancelAll() {
        notif1Runnable?.let { handler.removeCallbacks(it) }
        notif2Runnable?.let { handler.removeCallbacks(it) }
        notif1Runnable = null
        notif2Runnable = null
    }
}