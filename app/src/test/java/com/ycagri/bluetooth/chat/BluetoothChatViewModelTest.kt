package com.ycagri.bluetooth.chat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.datasource.DataRepository
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import java.util.*
import kotlin.collections.ArrayList

@RunWith(JUnit4::class)
class BluetoothChatViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val repository = mock(DataRepository::class.java)

    private val viewModel = BluetoothChatViewModel(repository)

    @Test
    fun testNotNull() {
        assertThat(viewModel.repository, notNullValue())
        verify(repository, never()).retrieveMessages(anyString())
        viewModel.setPairAddress("address1")
        verify(repository, never()).retrieveMessages(anyString())
    }

    @Test
    fun testLoadMessages() {
        viewModel.messages.observeForever(mock())
        viewModel.setPairAddress("address1")
        verify(repository).retrieveMessages("address1")
    }

    @Test
    fun sendMessagesToUI() {
        val messages = MutableLiveData<List<BluetoothMessage>>()
        `when`(repository.retrieveMessages("address1")).thenReturn(messages)
        val observer = mock<Observer<List<BluetoothMessage>>>()
        viewModel.messages.observeForever(observer)
        viewModel.setPairAddress("address1")
        verify(observer, never()).onChanged(any())

        val messageList = createMessageList()
        messages.value = messageList
        verify(observer).onChanged(messageList)
    }

    inline fun <reified T> mock(): T = mock(T::class.java)

    private fun createMessageList(): List<BluetoothMessage> {
        val messages = ArrayList<BluetoothMessage>()
        messages.add(
            BluetoothMessage(
                1, "Sender", "SenderAddress",
                "Receiver", "ReceiverAddress", 1, "Message",
                Date().time, 0
            )
        )
        return messages
    }
}