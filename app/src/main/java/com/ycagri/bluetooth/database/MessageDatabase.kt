package com.ycagri.bluetooth.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.database.model.BluetoothMessageDao

@Database(entities = [BluetoothMessage::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun bluetoothMessageDao(): BluetoothMessageDao
}