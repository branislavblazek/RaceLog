package com.blazek10.racelog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blazek10.racelog.R
import com.blazek10.racelog.ui.components.NameWithTime
import com.blazek10.racelog.ui.components.secondsToTime
import com.blazek10.racelog.ui.db.Athlete
import com.blazek10.racelog.ui.db.AthleteViewModel
import com.blazek10.racelog.ui.db.ControlPoint
import com.blazek10.racelog.ui.db.ControlPointNameViewModel
import com.blazek10.racelog.ui.db.RaceLog
import com.blazek10.racelog.ui.db.RaceLogsViewModel

@Composable
fun AthleteInfoScreen(
    athleteId: Int,
    modifier: Modifier = Modifier,
    athleteViewModel: AthleteViewModel = viewModel(),
    raceLogsViewModel: RaceLogsViewModel = viewModel(),
    controlPointNameViewModel: ControlPointNameViewModel = viewModel(),
) {
    athleteViewModel.getData(athleteId)
    raceLogsViewModel.getData(athleteId, null)

    val athleteData = athleteViewModel.state.value
    val raceLogsData = raceLogsViewModel.state.value
    val controlPointNames = controlPointNameViewModel.state.value
    val sortedRaceLogsData = raceLogsData.sortedBy { it.controlPointId }

    if (athleteData != null) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 24.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            InfoHeader(athlete = athleteData)

            Spacer(modifier = Modifier.size(16.dp))
            InfoContent(athlete = athleteData, logs = sortedRaceLogsData, names = controlPointNames)
        }
    }
}

@Composable
fun InfoHeader(
    athlete: Athlete,
    modifier: Modifier = Modifier
) {
    Row (
        modifier
    ) {
        Text(
            text = "#${athlete.bib}",
            style = TextStyle(fontSize=28.sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "${athlete.name} ${athlete.lastName}",
            style = TextStyle(fontSize=28.sp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InfoContent(
    athlete: Athlete,
    logs: List<RaceLog>,
    names: List<ControlPoint>,
    modifier: Modifier = Modifier
) {
    val age = 2024 - athlete.born
    val maxTime = logs.maxOfOrNull { it.time } ?: 0
    val formattedMaxTime = secondsToTime(maxTime)
    val isDnf = logs.find { it.dnf } != null
    val isFinished = logs.isNotEmpty() && logs[logs.size - 1].finished

    val stateString = if (isDnf) {
        stringResource(id = R.string.dnf)
    } else if (isFinished) {
        stringResource(id = R.string.finished)
    } else {
        stringResource(id = R.string.progress)
    }

    Column {
        Text(
            text = stringResource(id = R.string.status) + ": " + stateString,
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = stringResource(id = R.string.elapsed) + ": $formattedMaxTime",
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = stringResource(id = R.string.order) + ": " + if (isDnf) "-" else "...",
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = stringResource(id = R.string.country) + ": ${athlete.country}",
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = stringResource(id = R.string.age) + ": $age",
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = stringResource(id = R.string.team) + ": ${athlete.team}",
            style = TextStyle(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        for (log in logs) {
            val controlPointName = names.find { it.id == log.controlPointId }?.name ?: ""
            NameWithTime("#${log.controlPointId} $controlPointName", log.time)
        }
    }
}

