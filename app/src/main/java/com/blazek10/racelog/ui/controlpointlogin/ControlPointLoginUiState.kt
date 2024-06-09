package com.blazek10.racelog.ui.controlpointlogin

data class ControlPointLoginUiState(
    val controlPointName: String = "",
    val controlPointPass: String = "",
    val showPass: Boolean = false
)