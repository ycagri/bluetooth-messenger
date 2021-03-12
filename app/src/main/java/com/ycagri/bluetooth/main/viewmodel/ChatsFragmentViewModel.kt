package com.ycagri.bluetooth.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.datasource.DataRepository
import javax.inject.Inject

class ChatsFragmentViewModel @Inject constructor(val repository: DataRepository) : ViewModel() {

    private val _searchTerm: MutableLiveData<String> = MutableLiveData()

    val chats: LiveData<List<BluetoothMessage>> = Transformations.switchMap(_searchTerm) {
        if (it.isNullOrEmpty())
            return@switchMap repository.retrieveConversations()
        else
            return@switchMap repository.searchConversations("%$it%")
    }

    fun setSearchTerm(searchTerm: String) {
        if (_searchTerm.value == searchTerm) {
            return
        }

        _searchTerm.value = searchTerm
    }
}