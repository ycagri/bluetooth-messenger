package com.ycagri.bluetooth.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.viewmodel.ChatsFragmentViewModel
import com.ycagri.bluetooth.util.TestUtil
import com.ycagri.bluetooth.util.argumentCaptor
import com.ycagri.bluetooth.util.mock
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class ChatsFragmentViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val repository = mock(DataRepository::class.java)

    private val viewModel = ChatsFragmentViewModel(repository)

    @Test
    fun testNotNull() {
        assertThat(viewModel.repository, notNullValue())
        verify(repository, never()).retrieveConversations()
        viewModel.setSearchTerm("")
        verify(repository, never()).retrieveConversations()
    }

    @Test
    fun testRetrieveConversations() {
        viewModel.chats.observeForever(mock())
        viewModel.setSearchTerm("")
        verify(repository).retrieveConversations()
    }

    @Test
    fun testRetrieveConversationsToUI() {
        val messages = TestUtil.createMessages(10, "Test Message")
        `when`(repository.retrieveConversations()).thenReturn(MutableLiveData(messages))
        val observer = mock<Observer<List<BluetoothMessage>>>()
        viewModel.chats.observeForever(observer)
        viewModel.setSearchTerm("")
        verify(repository).retrieveConversations()

        val captor = argumentCaptor<List<BluetoothMessage>>()
        verify(observer).onChanged(captor.capture())
        for (i in 0 until 10) {
            assertEquals("Test Message ${i + 1}", captor.value[i].message)
            assertEquals((i + 1) % 2, captor.value[i].type)
        }
    }

    @Test
    fun testSearchConversations() {
        viewModel.chats.observeForever(mock())
        viewModel.setSearchTerm("search term")
        verify(repository).searchConversations("%search term%")
    }

    @Test
    fun testSearchConversationsToUI() {
        val messages = TestUtil.createMessages(10, "Test Message")
        `when`(repository.searchConversations(anyString())).thenReturn(MutableLiveData(messages))
        val observer = mock<Observer<List<BluetoothMessage>>>()
        viewModel.chats.observeForever(observer)
        viewModel.setSearchTerm("search term")
        verify(repository).searchConversations("%search term%")

        val captor = argumentCaptor<List<BluetoothMessage>>()
        verify(observer).onChanged(captor.capture())
        for (i in 0 until 10) {
            assertEquals("Test Message ${i + 1}", captor.value[i].message)
            assertEquals((i + 1) % 2, captor.value[i].type)
        }
    }
}