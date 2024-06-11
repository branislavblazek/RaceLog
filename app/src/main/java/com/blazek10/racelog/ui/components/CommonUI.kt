package com.blazek10.racelog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration

@Composable
fun BackgroundOverlay(
    imageRes: Int,
    overlayContent: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5f,
        )
        overlayContent()
    }
}

@Composable
fun NameWithTime(
    name: String,
    time: Number,
) {
    val formattedTime = secondsToTime(time)

    Column {
        Text(
            text = name,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = formattedTime,
            style = TextStyle(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

fun secondsToTime(input: Number): String {
    val hours = (input.toInt() / 3600).toString().padStart(2, '0')
    val minutes = ((input.toInt() % 3600) / 60).toString().padStart(2, '0')
    val seconds = (input.toInt() % 60).toString().padStart(2, '0')
    return "$hours:$minutes:$seconds"
}

fun formatDuration(duration: Duration): String {
    val hours = duration.toHours().toString().padStart(2, '0')
    val minutes = (duration.toMinutes() % 60).toString().padStart(2, '0')
    val seconds = (duration.seconds % 60).toString().padStart(2, '0')
    return "$hours:$minutes:$seconds"
}

fun isNumber(value: String): Boolean {
    return value.toIntOrNull() != null
}