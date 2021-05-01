package com.ycagri.bluetooth.di.activity

import com.ycagri.bluetooth.main.fragments.AvailableDevicesFragment
import com.ycagri.bluetooth.main.fragments.BondedDevicesFragment
import com.ycagri.bluetooth.main.fragments.ChatsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun chatFragment(): ChatsFragment

    @ContributesAndroidInjector
    abstract fun bondedDeviceFragments(): BondedDevicesFragment


    @ContributesAndroidInjector
    abstract fun availableDeviceFragments(): AvailableDevicesFragment
}