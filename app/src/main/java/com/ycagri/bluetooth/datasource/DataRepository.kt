package com.ycagri.bluetooth.datasource

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class DataRepository @Inject constructor(
    private val databaseCon: MessageDataSource,
    private val bluetoothCon: BluetoothDataConnection
) {
    fun insertMessage(message: BluetoothMessage): Long {
        return databaseCon.insertMessage(message)
    }

    fun retrieveMessages(
        pairAddress: String?
    ): LiveData<List<BluetoothMessage>> {
        return databaseCon.retrieveMessages(pairAddress, bluetoothCon.getAddress())
    }

    fun retrieveConversations(): LiveData<List<BluetoothMessage>> {
        return databaseCon.retrieveConversations(bluetoothCon.getAddress())
    }

    fun searchConversations(searchTerm: String): LiveData<List<BluetoothMessage>> {
        return databaseCon.searchConversations(searchTerm)
    }

    fun getPairedDevices(): LiveData<List<BluetoothDevice>> {
        return bluetoothCon.getPairedDevices()
    }

    fun searchPairedDevices(searchTerm: String): LiveData<List<BluetoothDevice>> {
        return Transformations.map(getPairedDevices()) {
            val filteredResults = ArrayList<BluetoothDevice>()
            for (d in it)
                if (d.name?.contains(searchTerm) == true)
                    filteredResults.add(d)

            return@map filteredResults
        }
    }

    fun getAvailableDevices(context: Context?): LiveData<List<BluetoothDevice>> {
        return bluetoothCon.getAvailableDevices(context)
    }

    fun startDiscovery(): Boolean {
        return bluetoothCon.startDiscovery()
    }
}