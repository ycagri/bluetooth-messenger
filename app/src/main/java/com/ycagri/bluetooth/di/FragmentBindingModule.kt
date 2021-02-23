package com.ycagri.bluetooth.di

import com.ycagri.bluetooth.di.fragment.BluetoothDevicesFragmentModule
import com.ycagri.bluetooth.di.fragment.ChatFragmentModule
import com.ycagri.bluetooth.main.fragments.AvailableDevicesFragment
import com.ycagri.bluetooth.main.fragments.BondedDevicesFragment
import com.ycagri.bluetooth.main.fragments.ChatsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [ChatFragmentModule::class])
    abstract fun chatFragment(): ChatsFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [BluetoothDevicesFragmentModule::class])
    abstract fun bondedDeviceFragments(): BondedDevicesFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [BluetoothDevicesFragmentModule::class])
    abstract fun availableDeviceFragments(): AvailableDevicesFragment
}