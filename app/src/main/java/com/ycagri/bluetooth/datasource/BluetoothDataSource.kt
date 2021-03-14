package com.ycagri.bluetooth.datasource

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.lifecycle.LiveData

interface BluetoothDataSource {

    fun getPairedDevices(): LiveData<List<BluetoothDevice>>

    fun getAvailableDevices(context: Context?): LiveData<List<BluetoothDevice>>

    fun startDiscovery(): Boolean

    fun getAddress(): String
}