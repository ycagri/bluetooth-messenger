package com.ycagri.bluetooth.datasource

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ycagri.awesomeui.utils.ReceiverLiveData
import javax.inject.Inject

class BluetoothDataConnection @Inject constructor(private val adapter: BluetoothAdapter) :
    BluetoothDataSource {

    private val availableDevices = HashSet<BluetoothDevice>()

    override fun getPairedDevices(): LiveData<List<BluetoothDevice>> {
        val liveData = MutableLiveData<List<BluetoothDevice>>()
        liveData.value = adapter.bondedDevices.toList()
        return liveData
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getAvailableDevices(context: Context): LiveData<List<BluetoothDevice>> {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        return ReceiverLiveData(context, filter) { _, intent ->
            val device: BluetoothDevice? =
                intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            if (device != null) {
                availableDevices.add(device)
            }

            return@ReceiverLiveData availableDevices.toList()
        }
    }

    override fun startDiscovery(): Boolean {
        return adapter.startDiscovery()
    }

    override fun getAddress() = adapter.address
}