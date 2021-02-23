package com.ycagri.awesomeui.chat.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ycagri.awesomeui.utils.AbsentLiveData

abstract class ChatViewModel<T, V> : ViewModel() {

    abstract val messages: LiveData<List<T>>

    private val _pairAddress: MutableLiveData<String> = MutableLiveData()
    val pairAddress: LiveData<String> get() = _pairAddress

    private val _messageToSend: MutableLiveData<String> = MutableLiveData()
    private val _textMessage: MutableLiveData<TextMessage> = MutableLiveData()
    val textMessage: LiveData<TextMessage> = Transformations.switchMap(_messageToSend) {
        _textMessage.value = TextMessage(pairAddress.value, _messageToSend.value)
        return@switchMap _textMessage
    }

    fun setPairAddress(address: String?) {
        if (_pairAddress.value == address) {
            return
        }

        _pairAddress.value = address
    }

    fun setMessage(message: String) {
        if (_messageToSend.value == message || message.isEmpty()) {
            return
        }

        _messageToSend.value = message
    }

    data class TextMessage(val receiverAddress: String?, val message: String?) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return if (receiverAddress.isNullOrEmpty() || message.isNullOrEmpty()) {
                AbsentLiveData.create()
            } else {
                f(receiverAddress, message)
            }
        }
    }
}