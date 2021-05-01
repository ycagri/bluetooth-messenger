package com.ycagri.bluetooth.core

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ycagri.bluetooth.di.AppInjector
import com.ycagri.bluetooth.service.BluetoothServerService
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class BluetoothApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this);
        ContextCompat.startForegroundService(this, Intent(this, BluetoothServerService::class.java))
    }

    override fun activityInjector() = dispatchingAndroidInjector
}