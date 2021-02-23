package com.ycagri.bluetooth.di.fragment

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.bluetooth.databinding.ItemBluetoothDeviceBinding
import com.ycagri.bluetooth.di.FragmentScoped
import com.ycagri.bluetooth.main.adapter.BluetoothDeviceAdapter
import com.ycagri.bluetooth.main.fragments.BondedDevicesFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class BluetoothDevicesFragmentModule {

    @Binds
    abstract fun provideAdapter(adapter: BluetoothDeviceAdapter): DataBoundRecyclerViewAdapter<BluetoothDevice, ItemBluetoothDeviceBinding>

    @Module
    companion object {
        @FragmentScoped
        @JvmStatic
        @Provides
        fun provideLayoutManager(context: Context): RecyclerView.LayoutManager {
            return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        @FragmentScoped
        @JvmStatic
        @Provides
        fun provideItemDecoration(context: Context): RecyclerView.ItemDecoration {
            return DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        }
    }

}