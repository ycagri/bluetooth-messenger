package com.ycagri.bluetooth.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_messages")
class BluetoothMessage(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "sender_name")
    val senderName: String,

    @ColumnInfo(name = "sender_address")
    val senderAddress: String,

    @ColumnInfo(name = "receiver_name")
    val receiverName: String,

    @ColumnInfo(name = "receiver_address")
    val receiverAddress: String,

    val type: Int,

    val message: String,

    val timestamp: Long,

    val status: Int
){
    companion object{
        const val TYPE_SENT = 0
        const val TYPE_RECEIVED = 1
    }
}