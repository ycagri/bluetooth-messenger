package com.ycagri.bluetooth.datasource

import androidx.lifecycle.LiveData
import com.ycagri.bluetooth.database.MessageDatabase
import com.ycagri.bluetooth.database.model.BluetoothMessage
import javax.inject.Inject

class MessageDataConnection @Inject constructor(private val db: MessageDatabase) :
    MessageDataSource {
    override fun insertMessage(message: BluetoothMessage): Long {
        return db.bluetoothMessageDao().insertMessage(message)
    }

    override fun retrieveMessages(
        pairAddress: String?,
        deviceAddress: String?
    ): LiveData<List<BluetoothMessage>> {
        return db.bluetoothMessageDao().retrieveMessages(pairAddress, deviceAddress)
    }

    override fun retrieveChats(deviceAddress: String?): LiveData<List<BluetoothMessage>> {
        return db.bluetoothMessageDao().retrieveChats(deviceAddress)
    }
}