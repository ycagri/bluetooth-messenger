package com.ycagri.bluetooth.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.databinding.ItemChatBinding

class ChatConversationAdapter constructor(
    appExecutors: AppExecutors,
    private val chatClickCallback: ((BluetoothMessage) -> Unit)?
) :
    DataBoundRecyclerViewAdapter<BluetoothMessage, ItemChatBinding>(
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
        }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemChatBinding {
        val binding = DataBindingUtil.inflate<ItemChatBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_chat, parent, false, null
        )
        binding.root.setOnClickListener {
            binding.message?.let {
                chatClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: ItemChatBinding, item: BluetoothMessage) {
        binding.message = item
    }

}