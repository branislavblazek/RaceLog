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

    fun onAction(actionId: Int) {
        val error = _uiState.value.bibValueInput.isEmpty()
                || !isNumber(_uiState.value.bibValueInput)
                || _uiState.value.bibValueInput.toInt() <= 0
                || _uiState.value.bibValueInput.toInt() > 1000

        _uiState.update { currentState -> currentState.copy(
            isError = error
        ) }

        if (!error) {
            // TODO: send
        }

        when (actionId) {
            0 -> {
                // DNF
            }
            1 -> {
                // DSQ
            }
            2 -> {
                // DNS
            }
            3 -> {
                // Send
            }
        }
    }

    fun onActionWithoutId() {
        onAction(_uiState.value.actionToPrompt)
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

    private fun isNumber(value: String): Boolean {
        return value.toIntOrNull() != null
    }
}