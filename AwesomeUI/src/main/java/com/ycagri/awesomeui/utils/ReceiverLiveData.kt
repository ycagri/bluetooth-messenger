package com.ycagri.awesomeui.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import java.util.function.BiFunction

@RequiresApi(Build.VERSION_CODES.N)
class ReceiverLiveData<T>(
    private val context: Context,
    private val filter: IntentFilter,
    private val func: BiFunction<Context?, Intent?, T>
) : LiveData<T>() {

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            value = func.apply(context, intent)
        }

    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(receiver)
    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(receiver, filter)
    }
}