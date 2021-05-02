package com.ycagri.awesomeui.chat

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.awesomeui.chat.mvvm.ChatViewModel
import com.ycagri.awesomeui.core.AppExecutors
import javax.inject.Inject

abstract class ChatActivity<T, V : ViewDataBinding, U> : AppCompatActivity() {

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var adapter: DataBoundRecyclerViewAdapter<T, V>

    protected abstract val viewModel: ChatViewModel<T, U>

    protected lateinit var toolbar: Toolbar

    protected lateinit var chatRV: RecyclerView

    protected lateinit var sendButton: View

    protected lateinit var messageET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initControls()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        chatRV.let {
            it.setHasFixedSize(true)
            it.adapter = adapter
        }

        sendButton.setOnClickListener {
            viewModel.setMessage(messageET.text.toString())
            messageET.text.clear()
        }

        viewModel.messages.observe(this, {
            adapter.submitList(it)
        })

        viewModel.textMessage.observe(this) {
            sendMessage(it)
        }
    }

    abstract fun getDevice(): U

    abstract fun initControls()

    abstract fun sendMessage(message: ChatViewModel.TextMessage)
}