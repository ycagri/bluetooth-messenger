package com.ycagri.bluetooth.core

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ycagri.bluetooth.di.AppComponent
import com.ycagri.bluetooth.di.DaggerAppComponent
import com.ycagri.bluetooth.service.BluetoothServerService
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BluetoothApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        ContextCompat.startForegroundService(this, Intent(this, BluetoothServerService::class.java))
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: AppComponent = DaggerAppComponent.builder()
            .application(this)
            .bluetoothAdapter(BluetoothAdapter.getDefaultAdapter())
            .build()

        component.inject(this)

        return component as AndroidInjector<out DaggerApplication>
    }
}