package com.ycagri.bluetooth

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class BluetoothTestApplicationRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, BluetoothTestApplication::class.java.name, context)
    }
}