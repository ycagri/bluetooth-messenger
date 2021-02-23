package com.ycagri.bluetooth.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ycagri.bluetooth.R
import dagger.android.DaggerService
import kotlin.random.Random

class NotificationWrapper {

    companion object {
        private const val CHANNEL_ID = "BluetoothForegroundServiceChannel"

        private const val CHANNEL_NAME = "Bluetooth Foreground Service"
    }

    fun crateNotificationChannel(context: Context) {
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
            channel.description = "Bluetooth messenger is working"

            val notificationManager =
                context.getSystemService(DaggerService.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(
        context: Context, message: String, ongoing: Boolean, category: String,
        resultPendingIntent: PendingIntent
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        val builder = notificationBuilder.setOngoing(ongoing)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(message)
            .setCategory(category)
            .setContentIntent(resultPendingIntent)

        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT)
            builder.priority = NotificationManager.IMPORTANCE_MIN

        return builder.build()
    }

    fun showNotification(context: Context, notification: Notification) {
        with(NotificationManagerCompat.from(context)) {
            notify(Random(Int.MAX_VALUE).nextInt(), notification)
        }
    }
}