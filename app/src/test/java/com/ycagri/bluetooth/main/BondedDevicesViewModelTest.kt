package com.ycagri.bluetooth.main

import android.bluetooth.BluetoothDevice
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.viewmodel.BondedDevicesViewModel
import com.ycagri.bluetooth.util.TestUtil
import com.ycagri.bluetooth.util.mock
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*

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
        verify(repository, never()).getPairedDevices()
    }

    @Test
    fun testRetrievePairedDevices() {
        viewModel.devices.observeForever(mock())
        viewModel.setSearchTerm("")
        verify(repository).getPairedDevices()
    }

    @Test
    fun testSearchConversations() {
        viewModel.devices.observeForever(mock())
        viewModel.setSearchTerm("search term")
        verify(repository).searchPairedDevices("search term")
    }

    @Test
    fun testRetrievePairedDevicesToUI() {
        val devices = TestUtil.createDevices(10, "Test Device", "test_address")
        `when`(repository.getPairedDevices()).thenReturn(MutableLiveData(devices))

        val observer = mock<Observer<List<BluetoothDevice>>>()
        viewModel.devices.observeForever(observer)
        viewModel.setSearchTerm("")
        verify(repository).getPairedDevices()
        for (i in 0 until 10) {
            assertEquals(devices[i].name, viewModel.devices.value?.get(i)?.name)
            assertEquals(devices[i].address, viewModel.devices.value?.get(i)?.address)
        }
    }

    @Test
    fun testSearchPairedDevicesToUI() {
        val devices = TestUtil.createDevices(10, "Test Device", "test_address")
        `when`(repository.searchPairedDevices("search term")).thenReturn(MutableLiveData(devices))

        val observer = mock<Observer<List<BluetoothDevice>>>()
        viewModel.devices.observeForever(observer)
        viewModel.setSearchTerm("search term")
        verify(repository).searchPairedDevices("search term")
        for (i in 0 until 10) {
            assertEquals(devices[i].name, viewModel.devices.value?.get(i)?.name)
            assertEquals(devices[i].address, viewModel.devices.value?.get(i)?.address)
        }
    }
}