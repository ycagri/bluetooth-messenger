package com.ycagri.bluetooth.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ycagri.awesomeui.chat.mvvm.ChatViewModel
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.datasource.DataRepository
import com.ycagri.bluetooth.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class BluetoothChatViewModel @Inject constructor(
    val repository: DataRepository
) : ChatViewModel<BluetoothMessage, String>() {

    override val messages: LiveData<List<BluetoothMessage>> =
        Transformations.switchMap(pairAddress) { input ->
            repository.retrieveMessages(input)
        }
}