package com.blazek10.racelog.ui.db

data class RaceLog (
    val athleteBib: Int = -1,
    val controlPointId: Int = -1,
    val dnf: Boolean = false,
    val dns: Boolean = false,
    val dsq: Boolean = false,
    val finished: Boolean = false,
    val time: Int = 0,
)