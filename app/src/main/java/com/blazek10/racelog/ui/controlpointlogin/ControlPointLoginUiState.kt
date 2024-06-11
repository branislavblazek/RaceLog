package com.blazek10.racelog.ui.controlpointlogin

data class ControlPointLoginUiState(
    val controlPointId: String = "",
    val controlPointPass: String = "",
    val showPass: Boolean = false
)