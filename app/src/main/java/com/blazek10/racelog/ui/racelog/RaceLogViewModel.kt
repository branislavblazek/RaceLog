package com.blazek10.racelog.ui.racelog

import androidx.lifecycle.ViewModel
import com.blazek10.racelog.ui.db.ControlPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RaceLogViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RaceLogUiState())
    val uiState: StateFlow<RaceLogUiState> = _uiState.asStateFlow()

    fun updateAthleteId(athleteId: Int) {
        _uiState.update { currentState -> currentState.copy(selectedAthleteId = athleteId) }
    }

    fun updateControlPoint(controlPointName: String, controlPointId: Int) {
        _uiState.update { currentState -> currentState.copy(
            selectedControlPointName = controlPointName,
            selectedControlPointId = controlPointId
        ) }
    }
}