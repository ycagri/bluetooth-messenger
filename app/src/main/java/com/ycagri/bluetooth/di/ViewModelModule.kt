package com.ycagri.bluetooth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ycagri.awesomeui.core.ViewModelFactory
import com.ycagri.bluetooth.main.viewmodel.AvailableDevicesViewModel
import com.ycagri.bluetooth.main.viewmodel.BondedDevicesViewModel
import com.ycagri.bluetooth.main.viewmodel.ChatsFragmentViewModel
import com.ycagri.bluetooth.chat.BluetoothChatViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChatsFragmentViewModel::class)
    abstract fun bindChatFragmentViewModel(chatFragmentViewModel: ChatsFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BluetoothChatViewModel::class)
    abstract fun bindBluetoothChatActivityViewModel(bluetoothChatActivityViewModel: BluetoothChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BondedDevicesViewModel::class)
    abstract fun bindBondedDevicesViewModel(bondedDevicesViewModel: BondedDevicesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AvailableDevicesViewModel::class)
    abstract fun bindAvailableDevicesViewModel(availableDevicesViewModel: AvailableDevicesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}