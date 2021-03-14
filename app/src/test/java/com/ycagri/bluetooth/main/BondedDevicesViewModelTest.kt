package com.ycagri.bluetooth.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.viewmodel.BondedDevicesViewModel
import com.ycagri.bluetooth.util.mock
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class BondedDevicesViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val repository = Mockito.mock(DataRepository::class.java)

    private val viewModel = BondedDevicesViewModel(repository)

    @Test
    fun testNotNull() {
        assertThat(viewModel.repository, notNullValue())
        Mockito.verify(repository, Mockito.never()).getPairedDevices()
    }

    @Test
    fun testRetrievePairedDevices() {
        viewModel.devices.observeForever(mock())
        viewModel.setSearchTerm("")
        Mockito.verify(repository).getPairedDevices()
    }

    @Test
    fun testSearchConversations() {
        viewModel.devices.observeForever(mock())
        viewModel.setSearchTerm("search term")
        Mockito.verify(repository).searchPairedDevices("search term")
    }
}