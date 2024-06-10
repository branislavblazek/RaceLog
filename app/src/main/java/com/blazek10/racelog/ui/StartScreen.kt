package com.blazek10.racelog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blazek10.racelog.R
import com.blazek10.racelog.ui.components.BackgroundOverlay

@Composable
fun StartScreen(
    onWatchRaceClicked: () -> Unit,
    onControlPointLoginClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BackgroundOverlay(
            imageRes = R.drawable.background) {
            ActionButtons(
                onWatchRaceClicked = onWatchRaceClicked,
                onControlPointLoginClicked = onControlPointLoginClicked,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ActionButtons(
    onWatchRaceClicked: () -> Unit,
    onControlPointLoginClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(
            onClick = onControlPointLoginClicked,
            shape = RoundedCornerShape(15),
            modifier = Modifier.widthIn(min = 300.dp)
        ) {
            Text(text = stringResource(R.string.control_point_login))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onWatchRaceClicked,
            shape = RoundedCornerShape(15),
            modifier = Modifier.widthIn(min = 300.dp)
        ) {
            Text(text = stringResource(R.string.watch_race))
        }
    }
}