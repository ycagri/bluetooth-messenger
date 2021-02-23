package com.ycagri.bluetooth.chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.awesomeui.chat.ChatActivity
import com.ycagri.awesomeui.chat.mvvm.ChatViewModel
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.bluetooth.BR
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.databinding.ItemBluetoothReceivedMessageBinding
import com.ycagri.bluetooth.databinding.ItemBluetoothSentMessageBinding
import com.ycagri.bluetooth.service.BluetoothClientService
import javax.inject.Inject

class BluetoothChatActivity :
    ChatActivity<BluetoothMessage, ViewDataBinding, String>() {

    override val viewModel: BluetoothChatViewModel by viewModels { viewModelFactory }

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

    override fun getDevice(): String {
        return intent.getStringExtra(KEY_DEVICE_ADDRESS)!!
    }

    companion object {
        private const val KEY_DEVICE_ADDRESS = "device_address"

        fun startActivity(context: Context, deviceAddress: String) {
            val intent = Intent(context, BluetoothChatActivity::class.java)
            intent.putExtra(KEY_DEVICE_ADDRESS, deviceAddress)
            context.startActivity(intent)
        }
    }

    override fun initControls() {
        setContentView(R.layout.activity_chat)

        toolbar = findViewById(R.id.toolbar)
        chatRV = findViewById(R.id.rv_chat)
        sendButton = findViewById(R.id.btn_send)
        messageET = findViewById(R.id.et_message)

        viewModel.setPairAddress(intent.getStringExtra(KEY_DEVICE_ADDRESS))
    }

    override fun sendMessage(message: ChatViewModel.TextMessage) {
        BluetoothClientService.sendMessage(this, message.receiverAddress, message.message!!)
    }
}