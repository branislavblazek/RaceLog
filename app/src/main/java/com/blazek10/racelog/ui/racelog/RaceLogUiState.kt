package com.blazek10.racelog.ui.racelog

data class RaceLogUiState (
    val selectedAthleteId: Int = -1,
    val selectedControlPointId: Int = -1,
    val selectedControlPointName: String = ""
)