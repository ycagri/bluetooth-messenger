package com.ycagri.bluetooth.di

import com.ycagri.bluetooth.service.BluetoothClientService
import com.ycagri.bluetooth.service.BluetoothServerService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    abstract fun bluetoothServerService(): BluetoothServerService

    @ContributesAndroidInjector
    abstract fun bluetoothClientService(): BluetoothClientService
}