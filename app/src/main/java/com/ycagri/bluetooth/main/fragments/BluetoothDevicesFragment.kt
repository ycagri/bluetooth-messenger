package com.ycagri.bluetooth.main.fragments

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ycagri.awesomeui.adapter.DataBoundRecyclerViewAdapter
import com.ycagri.awesomeui.core.AppExecutors
import com.ycagri.awesomeui.core.ViewModelFactory
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.chat.BluetoothChatActivity
import com.ycagri.bluetooth.databinding.ItemBluetoothDeviceBinding
import com.ycagri.bluetooth.di.Injectable
import com.ycagri.bluetooth.main.adapter.BluetoothDeviceAdapter
import javax.inject.Inject

abstract class BluetoothDevicesFragment : Fragment(), Injectable {

    companion object {
        private const val CODE_PERMISSION_REQUEST = 99

        private const val REQUEST_ENABLE_BT = 101
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    protected lateinit var adapter: DataBoundRecyclerViewAdapter<BluetoothDevice, ItemBluetoothDeviceBinding>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BluetoothDeviceAdapter(
            appExecutors = appExecutors,
        ) { device ->
            BluetoothChatActivity.startActivity(requireContext(), deviceAddress = device.address)
        }

        val devicesRV = view.findViewById<RecyclerView>(R.id.rv_bluetooth_devices)
        devicesRV.setHasFixedSize(true)
        devicesRV.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        devicesRV.adapter = adapter

        retrieveDevices()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK)
            retrieveDevices()
        else
            Snackbar.make(requireView(), R.string.warning_enable_bluetooth, Snackbar.LENGTH_LONG)
                .show()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CODE_PERMISSION_REQUEST) {
            var granted = true
            for (r in grantResults) {
                granted = granted && (r == PackageManager.PERMISSION_GRANTED)
            }

            if (granted) {
                retrieveDevices()
            } else {
                Snackbar.make(
                    requireView(),
                    R.string.warning_grant_permissions,
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getPermissionsToGrant(): List<String> {
        val permissions = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!isBackgroundLocationPermissionGranted())
                permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

            if (!isAccessFineLocationPermissionGranted())
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        return permissions
    }

    private fun askPermissions(permissions: Array<out String>) {
        requestPermissions(permissions, CODE_PERMISSION_REQUEST)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun isBackgroundLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isAccessFineLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isBluetoothEnabled(): Boolean {
        return BluetoothAdapter.getDefaultAdapter()?.isEnabled == true
    }

    private fun askForEnableBluetooth() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
    }

    private fun retrieveDevices() {
        val permissions = getPermissionsToGrant()
        if (permissions.isEmpty()) {
            if (isBluetoothEnabled())
                registerDevicesLiveData()
            else
                askForEnableBluetooth()
        } else {
            askPermissions(permissions.toTypedArray())
        }
    }

    abstract fun registerDevicesLiveData()
}