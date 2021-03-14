package com.ycagri.bluetooth.main.viewmodel

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.ycagri.bluetooth.datasource.DataRepository
import javax.inject.Inject

class BondedDevicesViewModel @Inject constructor(val repository: DataRepository) :
    ViewModel() {

    private val _searchTerm: MutableLiveData<String> = MutableLiveData()

    val devices: LiveData<List<BluetoothDevice>> = switchMap(_searchTerm) {
        if (it.isNullOrEmpty())
            return@switchMap repository.getPairedDevices()
        else
            return@switchMap repository.searchPairedDevices(it)
    }

    fun setSearchTerm(searchTerm: String) {
        if (_searchTerm.value == searchTerm) {
            return
        }

        _searchTerm.value = searchTerm
    }
}