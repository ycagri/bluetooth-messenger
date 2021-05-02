package com.ycagri.bluetooth.util

import com.ycagri.bluetooth.database.model.BluetoothMessage

object TestUtil {

    private const val RECEIVER_ADDRESS = "receiver_address"
    private const val RECEIVER_NAME = "Message Receiver"
    private const val SENDER_ADDRESS = "sender_address"
    private const val SENDER_NAME = "Message Sender"


    fun createMessage(id: Long, message: String, type: Int) = BluetoothMessage(
        id = id,
        message = message,
        receiverAddress = RECEIVER_ADDRESS,
        senderAddress = SENDER_ADDRESS,
        receiverName = RECEIVER_NAME,
        senderName = SENDER_NAME,
        status = 0,
        timestamp = System.currentTimeMillis(),
        type = type
    )

    fun createMessages(count: Int, message: String): List<BluetoothMessage> {
        return (1..count).map {
            BluetoothMessage(
                id = it.toLong(),
                message = "$message $it",
                receiverAddress = RECEIVER_ADDRESS,
                senderAddress = SENDER_ADDRESS,
                receiverName = RECEIVER_NAME,
                senderName = SENDER_NAME,
                status = 0,
                timestamp = System.currentTimeMillis(),
                type = it % 2
            )
        }
    }
}