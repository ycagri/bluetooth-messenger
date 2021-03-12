package com.ycagri.bluetooth.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.viewmodel.ChatsFragmentViewModel
import com.ycagri.bluetooth.util.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class ChatsFragmentViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val repository = Mockito.mock(DataRepository::class.java)

    private val viewModel = ChatsFragmentViewModel(repository)

    @Test
    fun testNotNull() {
        assertThat(viewModel.repository, notNullValue())
        Mockito.verify(repository, Mockito.never()).retrieveConversations()
        viewModel.setSearchTerm("")
        Mockito.verify(repository, Mockito.never()).retrieveConversations()
    }

    @Test
    fun testRetrieveConversations() {
        viewModel.chats.observeForever(mock())
        viewModel.setSearchTerm("")
        Mockito.verify(repository).retrieveConversations()
    }

    @Test
    fun testSearchConversations() {
        viewModel.chats.observeForever(mock())
        viewModel.setSearchTerm("search term")
        Mockito.verify(repository).searchConversations("%search term%")
    }
}