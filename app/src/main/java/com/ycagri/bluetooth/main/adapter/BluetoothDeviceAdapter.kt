package com.ycagri.bluetooth.main.adapter

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.bluetooth.databinding.ItemBluetoothDeviceBinding
import javax.inject.Inject

class BluetoothDeviceAdapter @Inject constructor(
    appExecutors: AppExecutors,
    private val deviceClickCallback: ((BluetoothDevice) -> Unit)?
) :
    DataBoundRecyclerViewAdapter<BluetoothDevice, ItemBluetoothDeviceBinding>(appExecutors,
        object : DiffUtil.ItemCallback<BluetoothDevice>() {
            override fun areItemsTheSame(
                oldItem: BluetoothDevice,
                newItem: BluetoothDevice
            ): Boolean {
                return oldItem.address == newItem.address
            }

            override fun areContentsTheSame(
                oldItem: BluetoothDevice,
                newItem: BluetoothDevice
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.address == newItem.address
            }
        }) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemBluetoothDeviceBinding {
        return ItemBluetoothDeviceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    }

    override fun bind(binding: ItemBluetoothDeviceBinding, item: BluetoothDevice) {
        binding.device = item
        binding.root.setOnClickListener {
            deviceClickCallback?.invoke(item)
        }
    }
}