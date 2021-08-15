package com.ycagri.bluetooth.di

import com.ycagri.bluetooth.chat.MessagesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MessagesFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindMessagesFragment(): MessagesFragment
}