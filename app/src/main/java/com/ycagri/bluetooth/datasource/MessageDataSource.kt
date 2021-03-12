package com.ycagri.bluetooth.datasource

import androidx.lifecycle.LiveData
import com.ycagri.bluetooth.database.model.BluetoothMessage

interface MessageDataSource {

    fun insertMessage(message: BluetoothMessage): Long

    fun retrieveMessages(pairAddress: String?, deviceAddress: String?): LiveData<List<BluetoothMessage>>

    fun retrieveConversations(deviceAddress: String?): LiveData<List<BluetoothMessage>>

    fun searchConversations(searchTerm: String): LiveData<List<BluetoothMessage>>
}