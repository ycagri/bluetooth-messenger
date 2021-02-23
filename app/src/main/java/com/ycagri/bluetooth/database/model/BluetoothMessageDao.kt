package com.ycagri.bluetooth.database.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class BluetoothMessageDao {

    @Insert
    abstract fun insertMessage(message: BluetoothMessage): Long

    @Query("Select * From tbl_messages Where sender_address=:pairAddress Or sender_address=:deviceAddress Order By timestamp DESC")
    abstract fun retrieveMessages(
        pairAddress: String?,
        deviceAddress: String?
    ): LiveData<List<BluetoothMessage>>

    @Query("Select * From tbl_messages Where sender_address!=:deviceAddress Group By sender_address Order By timestamp DESC")
    abstract fun retrieveChats(deviceAddress: String?): LiveData<List<BluetoothMessage>>
}