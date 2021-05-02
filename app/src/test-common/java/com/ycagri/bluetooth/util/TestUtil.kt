package com.ycagri.bluetooth.util

import android.bluetooth.BluetoothDevice
import com.ycagri.bluetooth.database.model.BluetoothMessage
import org.mockito.Mockito.`when`

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

    fun createBluetoothDevice(name: String, address: String): BluetoothDevice {
        val device = mock<BluetoothDevice>()
        `when`(device.name).thenReturn(name)
        `when`(device.address).thenReturn(address)
        return device
    }

    fun createDevices(count: Int, name: String, address: String): List<BluetoothDevice> {
        return (0 until count).map {
            createBluetoothDevice("$name ${it + 1}", "${address}_${it + 1}")
        }
    }
}