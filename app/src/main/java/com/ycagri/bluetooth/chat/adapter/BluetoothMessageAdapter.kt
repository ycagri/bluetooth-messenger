package com.ycagri.bluetooth.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.bluetooth.BR
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.databinding.ItemBluetoothReceivedMessageBinding
import com.ycagri.bluetooth.databinding.ItemBluetoothSentMessageBinding
import javax.inject.Inject

class BluetoothMessageAdapter @Inject constructor(appExecutors: AppExecutors) :
    DataBoundRecyclerViewAdapter<BluetoothMessage, ViewDataBinding>(
        appExecutors,
        object : DiffUtil.ItemCallback<BluetoothMessage>() {
            override fun areItemsTheSame(
                oldItem: BluetoothMessage,
                newItem: BluetoothMessage
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BluetoothMessage,
                newItem: BluetoothMessage
            ): Boolean {
                return oldItem.message == newItem.message && oldItem.timestamp == newItem.timestamp
            }
        }
    ) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return if (viewType == BluetoothMessage.TYPE_SENT)
            DataBindingUtil.inflate<ItemBluetoothSentMessageBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_bluetooth_sent_message, parent, false, null
            )
        else
            DataBindingUtil.inflate<ItemBluetoothReceivedMessageBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_bluetooth_received_message, parent, false, null
            )
    }

    override fun bind(binding: ViewDataBinding, item: BluetoothMessage) {
        binding.setVariable(BR.message, item)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}