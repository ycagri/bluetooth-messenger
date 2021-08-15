package com.ycagri.bluetooth.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ycagri.bluetooth.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BluetoothChatActivity : AppCompatActivity(), HasSupportFragmentInjector {

    companion object {
        private const val KEY_DEVICE_ADDRESS = "device_address"

        fun startActivity(context: Context, deviceAddress: String) {
            val intent = Intent(context, BluetoothChatActivity::class.java)
            intent.putExtra(KEY_DEVICE_ADDRESS, deviceAddress)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_messages_fragment,
                MessagesFragment.newInstance(intent.getStringExtra(KEY_DEVICE_ADDRESS)!!)
            )
            .commit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}