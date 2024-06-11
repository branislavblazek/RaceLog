package com.blazek10.racelog.ui.controlpoint

import java.time.LocalDateTime

data class ControlPointUiState(
    val bibValueInput: String = "",
    val startTime: LocalDateTime = LocalDateTime.of(2024, 6,11, 6,0),
    val currentTime: LocalDateTime = LocalDateTime.now(),
    val isError: Boolean = false,
    val expanded: Boolean = false,
    val openAlertDialog: Boolean = false,
    val actionToPrompt: Int = -1
)