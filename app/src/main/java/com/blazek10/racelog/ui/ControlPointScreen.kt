package com.blazek10.racelog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blazek10.racelog.R
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun ControlPointScreen(modifier: Modifier = Modifier) {
    var bibValueInput by remember { mutableStateOf("") }
    val startTime = LocalDateTime.of(2024, 6,9, 6,0)
    var currentTime by remember { mutableStateOf(LocalDateTime.now()) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = LocalDateTime.now()
        }
    }

    val duration = Duration.between(startTime, currentTime)

    fun onActionClick(action: Int) {
        val error = bibValueInput.isEmpty() || !isNumber(bibValueInput) || bibValueInput.toInt() <= 0 || bibValueInput.toInt() > 1000
        if (error) {
            isError = true
            return
        }

        isError = false
        when (action) {
            0 -> {
                // DNF
            }
            1 -> {
                // DSQ
            }
            2 -> {
                // DNS
            }
            3 -> {
                // Send
            }
        }
    }

    Column {
        Text(
            text = formatDuration(duration),
            fontSize = 56.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )
        Row (
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = bibValueInput,
                onValueChange = { bibValueInput = it
                    isError = false},
                label = { Text(stringResource(id = R.string.bib)) },
                placeholder = { Text(stringResource(id = R.string.bib_placeholder)) },
                leadingIcon = {
                    if (isError)
                        Icon(Icons.Filled.Warning, contentDescription = stringResource(id = R.string.bib_error), tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(Icons.Filled.Face, contentDescription = stringResource(id = R.string.bib)) },
                trailingIcon = {
                    if (bibValueInput.isNotEmpty())
                        ButtonWithIcon(
                            icon= Icons.Filled.Clear,
                            onClick = { bibValueInput = ""
                            isError = false}) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(onSend = {onActionClick(0)}),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            text = stringResource(id = R.string.bib_error),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
            IconButton(onClick = { onActionClick(0) }) {
                Icon(Icons.Filled.Send, contentDescription = null)
            }
            CancelRunActions(onActionClick= { onActionClick(it) })
        }
    }
}

@Composable
fun CancelRunActions(onActionClick: (action: Int) -> Unit) {
    var expanded by remember { mutableStateOf(false)}
    var openAlertDialog by remember { mutableStateOf(false)}
    var actionToPrompt by remember { mutableIntStateOf(-1) }

    fun promptAction(action: Int) {
        actionToPrompt = action
        openAlertDialog = true
    }

    Box(modifier = Modifier
        .wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.more))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                onClick = { promptAction(1) },
                text = { Text(text = stringResource(id = R.string.set_dnf)) }
            )
            DropdownMenuItem(
                onClick = { promptAction(2) },
                text = { Text(text = stringResource(id = R.string.set_dsq)) }
            )
            DropdownMenuItem(
                onClick = { promptAction(3) },
                text = { Text(text = stringResource(id = R.string.set_dns)) }
            )
        }
    }

    if (openAlertDialog) {
        AlertDialog(
            onDismissRequest = {
                openAlertDialog = false
                expanded = false},
            icon = { Icon(Icons.Filled.Warning, contentDescription = stringResource(id = R.string.remove_athlete_title)) },
            title = { Text(text = stringResource(id = R.string.remove_athlete_title)) },
            text = { Text(text = stringResource(id = R.string.remove_athlete_text)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                        expanded = false
                        onActionClick(actionToPrompt)},
                ) {
                    Text(text = stringResource(id = R.string.remove))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                        expanded = false},
                ) {
                    Text(text = stringResource(id = R.string.keep))
                }
            }
        )
    }
}

private fun formatDuration(duration: Duration): String {
    val hours = duration.toHours().toString().padStart(2, '0')
    val minutes = (duration.toMinutes() % 60).toString().padStart(2, '0')
    val seconds = (duration.seconds % 60).toString().padStart(2, '0')
    return "$hours:$minutes:$seconds"
}

private fun isNumber(value: String): Boolean {
    return value.toIntOrNull() != null
}