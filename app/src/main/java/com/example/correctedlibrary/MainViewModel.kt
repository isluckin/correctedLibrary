package com.example.correctedlibrary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _itemList = MutableLiveData<List<Item>>()
    val itemList = _itemList

    fun updateLibrary(list: List<Item>) {
        val oldList = _itemList.value
        _itemList.value = oldList?.plus(list) ?: list
    }
}