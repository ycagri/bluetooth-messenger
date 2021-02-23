package com.ycagri.bluetooth.main.viewmodel

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ycagri.bluetooth.datasource.DataRepository
import javax.inject.Inject

class BondedDevicesViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    val devices: LiveData<List<BluetoothDevice>> = repository.getPairedDevices()
}