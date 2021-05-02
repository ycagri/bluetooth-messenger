package com.ycagri.bluetooth.main

import android.bluetooth.BluetoothDevice
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.viewmodel.AvailableDevicesViewModel
import com.ycagri.bluetooth.util.TestUtil
import com.ycagri.bluetooth.util.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class AvailableDevicesViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val repository = mock(DataRepository::class.java)

    private val viewModel = AvailableDevicesViewModel(repository)

    @Test
    fun testNotNull() {
        assertThat(viewModel.repository, notNullValue())
        verify(repository, never()).getAvailableDevices(any())
    }

    @Test
    fun testNotRetrieveAvailableDevices() {
        viewModel.devices.observeForever(mock())
        viewModel.setDiscovering(mock(), false)
        verify(repository, never()).getAvailableDevices(any())
    }

    @Test
    fun testRetrieveAvailableDevices() {
        viewModel.devices.observeForever(mock())
        viewModel.setDiscovering(mock(), true)
        verify(repository).getAvailableDevices(any())
    }

    @Test
    fun testRetrieveAvailableDevicesToUI() {
        val devices = TestUtil.createDevices(10, "Test Device", "test_address")
        `when`(repository.getAvailableDevices(any())).thenReturn(MutableLiveData(devices))

        val observer = mock<Observer<List<BluetoothDevice>>>()
        viewModel.devices.observeForever(observer)
        viewModel.setDiscovering(mock(), true)
        verify(repository).getAvailableDevices(any())

        for (i in 0 until 10) {
            Assert.assertEquals(devices[i].name, viewModel.devices.value?.get(i)?.name)
            Assert.assertEquals(devices[i].address, viewModel.devices.value?.get(i)?.address)
        }
    }
}