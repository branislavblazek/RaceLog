package com.blazek10.racelog.ui.athletelist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AthleteListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AthleteListUiState())
    val uiState: StateFlow<AthleteListUiState> = _uiState.asStateFlow()

    fun updateSearchText(searchText: String) {
        _uiState.value = _uiState.value.copy(searchText = searchText)
    }
}