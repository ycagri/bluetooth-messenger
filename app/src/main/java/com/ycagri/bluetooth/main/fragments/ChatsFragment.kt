package com.ycagri.bluetooth.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.main.adapter.ChatConversationAdapter
import com.ycagri.bluetooth.main.viewmodel.ChatsFragmentViewModel
import com.ycagri.bluetooth.chat.BluetoothChatActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ChatsFragment : DaggerFragment() {

    @Inject
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var adapter: ChatConversationAdapter

    @Inject
    lateinit var itemDecoration: RecyclerView.ItemDecoration

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel: ChatsFragmentViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChatConversationAdapter(
            appExecutors = appExecutors,
        ) { chat ->
            BluetoothChatActivity.startActivity(requireContext(), chat.senderAddress)
        }

        val rv_chats = view.findViewById<RecyclerView>(R.id.rv_chats)
        rv_chats.setHasFixedSize(true)
        rv_chats.layoutManager = layoutManager
        rv_chats.addItemDecoration(itemDecoration)
        rv_chats.adapter = adapter

        viewModel.chats.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }
}