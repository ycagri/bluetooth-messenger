package com.ycagri.bluetooth.di

import com.ycagri.bluetooth.chat.BluetoothChatActivity
import com.ycagri.bluetooth.di.activity.BluetoothChatActivityModule
import com.ycagri.bluetooth.di.activity.MainActivityModule
import com.ycagri.bluetooth.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [BluetoothChatActivityModule::class])
    abstract fun messageListActivity(): BluetoothChatActivity
}