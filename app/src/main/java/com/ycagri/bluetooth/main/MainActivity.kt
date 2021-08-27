package com.ycagri.bluetooth.main

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.main.fragments.AvailableDevicesFragment
import com.ycagri.bluetooth.main.fragments.BondedDevicesFragment
import com.ycagri.bluetooth.main.fragments.ChatsFragment
import com.ycagri.bluetooth.service.BluetoothServerService
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private val titles: Array<Int> = arrayOf(
        R.string.chats,
        R.string.paired_devices,
        R.string.available_devices
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.tool_bar))
        findViewById<ViewPager2>(R.id.vp_fragments).adapter = MainPagerAdapter(this)
        TabLayoutMediator(
            findViewById(R.id.tab_layout),
            findViewById(R.id.vp_fragments)
        ) { tab, position ->
            tab.text = getString(titles[position])
        }.attach()
    }

    private fun makeDeviceDiscoverable() {
        val discoverableIntent: Intent =
            Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            }
        startActivity(discoverableIntent)
    }

    private class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        companion object {
            private const val CHILD_COUNT = 3
        }

        override fun getItemCount() = CHILD_COUNT

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ChatsFragment()
                1 -> BondedDevicesFragment()
                2 -> AvailableDevicesFragment()
                else -> throw IllegalArgumentException("Position must be between 0, 1 or 2. Given: $position")
            }
        }
    }
}