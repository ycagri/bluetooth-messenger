package com.ycagri.bluetooth.service

import android.app.Service
import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.utils.AcceptThread
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import java.util.concurrent.Executors
import javax.inject.Inject


class BluetoothServerService : LifecycleService() {

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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        acceptThread = AcceptThread(this, true, repository)
        executor.submit(acceptThread)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }
}