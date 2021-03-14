package com.ycagri.bluetooth.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.main.viewmodel.AvailableDevicesViewModel
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
}