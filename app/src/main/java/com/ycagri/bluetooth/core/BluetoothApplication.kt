package com.ycagri.bluetooth.core

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Intent
import com.ycagri.bluetooth.di.AppInjector
import com.ycagri.bluetooth.service.BluetoothServerService
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject


class BluetoothApplication : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
        startService(Intent(this, BluetoothServerService::class.java))
    }

    override fun activityInjector() = dispatchingAndroidInjector

    override fun serviceInjector() = dispatchingServiceInjector
}