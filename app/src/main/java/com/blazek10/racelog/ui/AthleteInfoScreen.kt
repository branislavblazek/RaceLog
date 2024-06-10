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
import com.blazek10.racelog.ui.db.Athlete
import com.blazek10.racelog.ui.db.AthleteViewModel

@Composable
fun AthleteInfoScreen(
    athleteId: Int,
    modifier: Modifier = Modifier,
    athleteViewModel: AthleteViewModel = viewModel()
) {
    athleteViewModel.getData(athleteId)
    val athleteData = athleteViewModel.state.value

    if (athleteData != null) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 24.dp, end=16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            InfoHeader(athlete = athleteData)

            Spacer(modifier = Modifier.size(16.dp))
            InfoContent(athlete = athleteData, modifier)
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
    modifier: Modifier = Modifier
) {
    val age = 2024 - athlete.born

    Column {
        Text(
            text = stringResource(id = R.string.status) + ": " + stringResource(id = R.string.progress),
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = stringResource(id = R.string.elapsed) + ": 12:34:56",
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = stringResource(id = R.string.order) + ": 21/234",
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
        for (i in 1..10) {
            PassedControlPoint()
        }
    }
}

@Composable
fun PassedControlPoint() {
    Column {
        Text(
            text = "Control Point 1",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "12:34:56 | 03:46:09 | 21/234 | +12% | -88%",
            style = TextStyle(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}