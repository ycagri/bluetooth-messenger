package com.ycagri.bluetooth.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.MainActivity
import com.ycagri.bluetooth.utils.AcceptThread
import com.ycagri.bluetooth.utils.NotificationWrapper
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import java.util.concurrent.Executors
import javax.inject.Inject


class BluetoothServerService : Service() {

    companion object {
        private const val SERVICE_ID = 77
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var repository: DataRepository

    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var acceptThread: AcceptThread

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        acceptThread = AcceptThread(this, true, repository)
        executor.submit(acceptThread)

        val notificationWrapper = NotificationWrapper()
        notificationWrapper.crateNotificationChannel(this)
        val notification = notificationWrapper.createNotification(
            this, getString(R.string.service_description),
            true, Notification.CATEGORY_SERVICE, createPendingIntent()
        )
        startForeground(SERVICE_ID, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }

    private fun createPendingIntent(): PendingIntent {
        val resultIntent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, resultIntent, 0)
    }

}