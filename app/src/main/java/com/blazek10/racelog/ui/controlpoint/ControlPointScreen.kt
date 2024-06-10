package com.blazek10.racelog.ui.controlpoint

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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blazek10.racelog.R
import com.blazek10.racelog.ui.ButtonWithIcon
import kotlinx.coroutines.delay
import java.time.Duration

@Composable
fun ControlPointScreen(
    modifier: Modifier = Modifier,
    controlPointViewModel: ControlPointViewModel = viewModel()
) {
    val controlPointUiState by controlPointViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            controlPointViewModel.updateCurrentTime()
        }
    }

    val duration = Duration.between(
        controlPointUiState.startTime,
        controlPointUiState.currentTime)

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
                value = controlPointUiState.bibValueInput,
                onValueChange = { controlPointViewModel.updateBibValueInput(it) },
                label = { Text(stringResource(id = R.string.bib)) },
                placeholder = { Text(stringResource(id = R.string.bib_placeholder)) },
                leadingIcon = {
                    if (controlPointUiState.isError)
                        Icon(Icons.Filled.Warning, contentDescription = stringResource(id = R.string.bib_error), tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(Icons.Filled.Face, contentDescription = stringResource(id = R.string.bib)) },
                trailingIcon = {
                    if (controlPointUiState.bibValueInput.isNotEmpty())
                        ButtonWithIcon(
                            icon= Icons.Filled.Clear,
                            onClick = { controlPointViewModel.clearBibValueInput() })},
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(onSend = { controlPointViewModel.onAction(0) }),
                isError = controlPointUiState.isError,
                supportingText = {
                    if (controlPointUiState.isError) {
                        Text(
                            text = stringResource(id = R.string.bib_error),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
            IconButton(onClick = { controlPointViewModel.onAction(0)}) {
                Icon(Icons.Filled.Send, contentDescription = null)
            }
            CancelRunActions(
                openAlertDialog = controlPointUiState.openAlertDialog,
                isExpanded = controlPointUiState.expanded,
                toggleExpanded = { controlPointViewModel.toggleExpanded() },
                closeExpanded = { controlPointViewModel.closeExpanded() },
                closeAll = { controlPointViewModel.closeAll() },
                promptAction = { controlPointViewModel.promptAction(it) },
                onActionClick= { controlPointViewModel.onActionWithoutId() }
            )
        }
    }
}

@Composable
fun CancelRunActions(
    openAlertDialog: Boolean = false,
    isExpanded: Boolean = false,
    toggleExpanded: () -> Unit,
    closeExpanded: () -> Unit,
    closeAll: () -> Unit,
    promptAction: (action: Int) -> Unit,
    onActionClick: () -> Unit
) {

    Box(modifier = Modifier
        .wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = toggleExpanded) {
            Icon(Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.more))
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { closeExpanded() },
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
            onDismissRequest = { closeAll() },
            icon = { Icon(Icons.Filled.Warning, contentDescription = stringResource(id = R.string.remove_athlete_title)) },
            title = { Text(text = stringResource(id = R.string.remove_athlete_title)) },
            text = { Text(text = stringResource(id = R.string.remove_athlete_text)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        closeAll()
                        onActionClick()},
                ) {
                    Text(text = stringResource(id = R.string.remove))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { closeAll() },
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