package com.ycagri.bluetooth.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.main.viewmodel.AvailableDevicesViewModel

class AvailableDevicesFragment : BluetoothDevicesFragment() {

    private val viewModel: AvailableDevicesViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bluetooth_devices, container, false)
    }

    override fun registerDevicesLiveData() {
        viewModel.devices.observe(viewLifecycleOwner, { devices ->
            adapter.submitList(devices)
        })

        viewModel.setDiscovering(requireContext(), true)
    }
}