package com.ycagri.bluetooth.main.viewmodel

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ycagri.awesomeui.utils.AbsentLiveData
import com.ycagri.bluetooth.datasource.DataRepository
import javax.inject.Inject

class AvailableDevicesViewModel @Inject constructor(val repository: DataRepository) :
    ViewModel() {

    private val _isDiscovering: MutableLiveData<BluetoothDiscovery> = MutableLiveData()

    val devices: LiveData<List<BluetoothDevice>> = Transformations.switchMap(_isDiscovering) {
        it.ifExists { context ->
            repository.getAvailableDevices(context)
        }
    }

    fun setDiscovering(context: Context, discovering: Boolean) {
        val update = BluetoothDiscovery(context, discovering)
        if (_isDiscovering.value?.isDiscovering == update.isDiscovering) {
            return
        }

        _isDiscovering.value = update
        repository.startDiscovery()
    }

    data class BluetoothDiscovery(val context: Context, val isDiscovering: Boolean) {
        fun <T> ifExists(f: (Context) -> LiveData<T>): LiveData<T> {
            return if (!isDiscovering) {
                AbsentLiveData.create()
            } else {
                f(context)
            }
        }
    }
}