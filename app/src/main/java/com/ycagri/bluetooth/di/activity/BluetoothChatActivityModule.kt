package com.ycagri.bluetooth.di.activity

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.chat.BluetoothChatActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class BluetoothChatActivityModule {

    @Binds
    abstract fun provideAdapter(adapter: BluetoothChatActivity.BluetoothMessageAdapter): DataBoundRecyclerViewAdapter<BluetoothMessage, ViewDataBinding>

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideLayoutManager(activity: BluetoothChatActivity): RecyclerView.LayoutManager {
            return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        }
    }
}