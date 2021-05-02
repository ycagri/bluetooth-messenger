package com.ycagri.bluetooth.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.chat.BluetoothChatActivity
import com.ycagri.bluetooth.di.Injectable
import com.ycagri.bluetooth.main.adapter.ChatConversationAdapter
import com.ycagri.bluetooth.main.viewmodel.ChatsFragmentViewModel
import javax.inject.Inject

class ChatsFragment : Fragment(), Injectable {

    lateinit var adapter: ChatConversationAdapter

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
        rv_chats.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        rv_chats.adapter = adapter

        view.findViewById<TextInputEditText>(R.id.et_chat_search)
            .setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSystemService(requireContext(), InputMethodManager::class.java)?.hideSoftInputFromWindow(v.windowToken, 0)
                    viewModel.setSearchTerm(v.text.toString())
                    return@setOnEditorActionListener true
                }

                return@setOnEditorActionListener false
            }

        viewModel.chats.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.setSearchTerm("")
    }
}