package com.ycagri.bluetooth.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.chat.adapter.BluetoothMessageAdapter
import com.ycagri.bluetooth.di.Injectable
import com.ycagri.bluetooth.service.BluetoothClientService
import javax.inject.Inject

class MessagesFragment : Fragment(), Injectable {

    companion object {
        private const val KEY_DEVICE_ADDRESS = "device_address"

        fun newInstance(address: String): Fragment {
            val f = MessagesFragment()
            val args = Bundle()
            args.putString(KEY_DEVICE_ADDRESS, address)
            f.arguments = args
            return f
        }
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: BluetoothChatViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BluetoothMessageAdapter(appExecutors)
        view.findViewById<RecyclerView>(R.id.rv_messages).let {
            it.setHasFixedSize(true)
            it.adapter = adapter
        }

        view.findViewById<View>(R.id.btn_send).setOnClickListener {
            val et = view.findViewById<EditText>(R.id.et_message)
            viewModel.setMessage(et.text.toString())
            et.text.clear()
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.textMessage.observe(viewLifecycleOwner) {
            BluetoothClientService.sendMessage(requireContext(), it.receiverAddress, it.message!!)
        }

        viewModel.setPairAddress(arguments?.getString(KEY_DEVICE_ADDRESS))
    }

}