package com.blazek10.racelog.ui.controlpoint

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ControlPointViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ControlPointUiState())
    val uiState: StateFlow<ControlPointUiState> = _uiState.asStateFlow()

    fun updateCurrentTime() {
        _uiState.update { currentState -> currentState.copy(
            currentTime = currentState.currentTime.plusSeconds(1)
        ) }
    }

    fun updateBibValueInput(bibValueInput: String) {
        _uiState.update { currentState -> currentState.copy(
            bibValueInput = bibValueInput,
            isError = false
        ) }
    }

    fun clearBibValueInput() {
        _uiState.update { currentState -> currentState.copy(
            bibValueInput = "",
            isError = false
        ) }
    }

    fun setError(value: Boolean) {
        _uiState.update { currentState -> currentState.copy(
            isError = value
        ) }
    }

    fun promptAction(actionId: Int) {
        _uiState.update { currentState -> currentState.copy(
            actionToPrompt = actionId,
            openAlertDialog = true,
        ) }
    }

    fun toggleExpanded() {
        _uiState.update { currentState -> currentState.copy(
            expanded = !currentState.expanded
        ) }
    }

    fun closeAll() {
        _uiState.update { currentState -> currentState.copy(
            expanded = false,
            openAlertDialog = false
        ) }
    }

    fun closeExpanded() {
        _uiState.update { currentState -> currentState.copy(
            expanded = false
        ) }
    }

    fun setActionToPrompt(actionId: Int) {
        _uiState.update { currentState -> currentState.copy(
            actionToPrompt = actionId
        ) }
    }
}