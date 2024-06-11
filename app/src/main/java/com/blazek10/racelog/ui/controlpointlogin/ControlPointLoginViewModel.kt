package com.blazek10.racelog.ui.controlpointlogin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ControlPointLoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ControlPointLoginUiState())
    val uiState: StateFlow<ControlPointLoginUiState> = _uiState.asStateFlow()

    fun updateId(id: String) {
       _uiState.update { currentState -> currentState.copy(controlPointId = id) }
    }

    fun updatePass(pass: String) {
       _uiState.update { currentState -> currentState.copy(controlPointPass = pass) }
    }

    fun toggleShowPass() {
        _uiState.update { currentState -> currentState.copy(showPass = !currentState.showPass) }
    }

    fun reset() {
        _uiState.update { ControlPointLoginUiState() }
    }
}