package com.ycagri.bluetooth.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.MainActivity
import java.io.IOException
import java.util.*

class AcceptThread(
    private val context: Context,
    private val secure: Boolean,
    private val repository: DataRepository
) : Thread() {

    companion object {
        private val SECURE_UUID = UUID.fromString("1c0c62b0-694b-11eb-9439-0242ac130002")

        private val INSECURE_UUID = UUID.fromString("9d653784-845f-495e-ad84-545f2fbc1fc2")

        private const val SECURE_NAME = "BluetoothMessageSecure"

        private const val INSECURE_NAME = "BluetoothMessageInsecure"

        private const val BUFFER_SIZE = 4096

        private val BUFFER = ByteArray(BUFFER_SIZE) { 0 }
    }

    private val socket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        if (secure)
            BluetoothAdapter.getDefaultAdapter()
                ?.listenUsingRfcommWithServiceRecord(SECURE_NAME, SECURE_UUID)
        else
            BluetoothAdapter.getDefaultAdapter()
                ?.listenUsingInsecureRfcommWithServiceRecord(INSECURE_NAME, INSECURE_UUID)
    }

    private val deviceName: String by lazy(LazyThreadSafetyMode.NONE) {
        BluetoothAdapter.getDefaultAdapter().name
    }

    private val deviceAddress: String by lazy(LazyThreadSafetyMode.NONE) {
        BluetoothAdapter.getDefaultAdapter().address
    }

    override fun run() {
        while (true) {
            val s: BluetoothSocket? = try {
                socket?.accept()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            s?.use {
                try {
                    val message = readMessage(it)
                    sendAck(it)
                    insertMessage(it.remoteDevice.name, it.remoteDevice.address, message)
                    showNotification(message)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun readMessage(s: BluetoothSocket): String {
        val inputStream = s.inputStream
        val read = inputStream?.read(BUFFER, 0, BUFFER_SIZE) ?: -1
        if (read > 0)
            return String(BUFFER, 0, read)

        throw IOException("Data can't be read!")
    }

    private fun insertMessage(senderName: String, senderAddress: String, message: String) {
        repository.insertMessage(
            BluetoothMessage(
                0,
                senderName,
                senderAddress,
                deviceName,
                deviceAddress,
                BluetoothMessage.TYPE_RECEIVED,
                message,
                System.currentTimeMillis(),
                0
            )
        )
    }

    private fun showNotification(message: String){
        val wrapper = NotificationWrapper()
        val resultIntent = Intent(context, MainActivity::class.java)
        with(wrapper.createNotification(context, message, false, Notification.CATEGORY_MESSAGE,
            PendingIntent.getActivity(context, 0, resultIntent, 0))){
            wrapper.showNotification(context, this)
        }
    }

    private fun sendAck(s: BluetoothSocket) {
        s.outputStream?.write(1)
    }
}