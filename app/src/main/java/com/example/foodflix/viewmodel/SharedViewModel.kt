package com.example.foodflix.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {
    private val _canSearch = MutableStateFlow(false)
    val canSearch = _canSearch.asStateFlow()

    private val _searchFieldVisible = MutableStateFlow(false)
    val searchFieldVisible = _searchFieldVisible.asStateFlow()

    fun setCanSearch(value: Boolean) {
        _canSearch.value = value
    }

    fun setSearchFieldVisible(value: Boolean) {
        _searchFieldVisible.value = value
    }
}
