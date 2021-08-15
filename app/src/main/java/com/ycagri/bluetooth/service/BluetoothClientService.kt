package com.ycagri.bluetooth.service

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.datasource.DataRepository
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import java.util.*
import javax.inject.Inject

class BluetoothClientService : JobIntentService() {

    companion object {

        private val SECURE_UUID = UUID.fromString("1c0c62b0-694b-11eb-9439-0242ac130002")

        private val INSECURE_UUID = UUID.fromString("9d653784-845f-495e-ad84-545f2fbc1fc2")

        private const val KEY_MESSAGE = "message"

        private const val KEY_ADDRESS = "address"

        fun sendMessage(context: Context, device: String?, message: String) {
            val intent = Intent(context, BluetoothClientService::class.java)
            intent.putExtra(KEY_MESSAGE, message)
            intent.putExtra(KEY_ADDRESS, device)
            enqueueWork(context, BluetoothClientService::class.java, 1, intent)
        }
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var repository: DataRepository

    @Inject
    lateinit var adapter: BluetoothAdapter

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onHandleWork(intent: Intent) {
        val device: String? = intent.getStringExtra(KEY_ADDRESS)
        val message = intent.getStringExtra(KEY_MESSAGE)

        var pairDevice: BluetoothDevice? = null
        for (d in adapter.bondedDevices) {
            if (d.address == device) {
                pairDevice = d
                break
            }
        }

        if (message != null)
            sendMessage(pairDevice, message)
    }

    private fun sendMessage(device: BluetoothDevice?, message: String) {
        val socket = device?.createRfcommSocketToServiceRecord(SECURE_UUID)
        socket?.use {
            try {
                it.connect()
                if (it.isConnected) {
                    transmitMessage(it, message)
                    saveMessage(device, message, it)
                    receiveAck(it)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun transmitMessage(s: BluetoothSocket, message: String) {
        val outputStream = s.outputStream
        outputStream.write(message.toByteArray())
    }

    private fun saveMessage(device: BluetoothDevice, message: String, socket: BluetoothSocket) {
        repository.insertMessage(
            BluetoothMessage(
                0,
                device.name,
                device.address,
                socket.remoteDevice.name,
                socket.remoteDevice.address,
                BluetoothMessage.TYPE_SENT,
                message,
                System.currentTimeMillis(),
                0
            )
        )
    }

    private fun receiveAck(s: BluetoothSocket): Boolean {
        val ack = s.inputStream.read()
        return ack == 1
    }
}