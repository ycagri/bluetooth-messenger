package com.ycagri.bluetooth.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.datasource.DataRepository
import javax.inject.Inject

class ChatFragmentViewModel @Inject constructor(dataRepository: DataRepository) : ViewModel() {

    val chats: LiveData<List<BluetoothMessage>> = dataRepository.retrieveChats()
}