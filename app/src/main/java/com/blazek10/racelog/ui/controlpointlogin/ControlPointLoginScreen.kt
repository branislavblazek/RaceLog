package com.blazek10.racelog.ui.controlpointlogin

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blazek10.racelog.R
import com.blazek10.racelog.ui.components.BackgroundOverlay

@Composable
fun ControlPointLoginScreen(
    modifier: Modifier = Modifier,
    controlPointLoginViewModel: ControlPointLoginViewModel = viewModel()
) {
    val controlPointLoginUiState by controlPointLoginViewModel.uiState.collectAsState()

    BackgroundOverlay(imageRes = R.drawable.background_login) {
        LoginContent(
            controlPointLoginUiState.controlPointName,
            { controlPointLoginViewModel.updateName(it) },
            controlPointLoginUiState.controlPointPass,
            { controlPointLoginViewModel.updatePass(it) },
            controlPointLoginUiState.showPass,
            { controlPointLoginViewModel.toggleShowPass() },
            modifier
        )
    }
}

@Composable
fun LoginContent(
    nameValue: String,
    onNameValueChange: (String) -> Unit,
    passValue: String,
    onPassValueChange: (String) -> Unit,
    showPass: Boolean,
    toggleShowPass: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .padding(40.dp)
            .then(modifier)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.control_point_login),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        LoginInput(
            label = R.string.id,
            leadingIcon = Icons.Filled.Home,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = nameValue,
            onValueChange = onNameValueChange
        )
        LoginInput(
            label = R.string.password,
            leadingIcon = Icons.Filled.Lock,
            onTrailingIconClick = { toggleShowPass() },
            trailingIcon = if (showPass) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            value = passValue,
            onValueChange = onPassValueChange,
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation()
        )
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(15),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.login)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(Icons.Filled.Send, contentDescription = null)
            }
        }

    }
}

@Composable
fun LoginInput(
    @StringRes label: Int,
    leadingIcon: ImageVector,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = label)) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null) },
        trailingIcon = { if (value.isNotEmpty() && trailingIcon != null) {
            IconButton(onClick = {if (onTrailingIconClick != null) onTrailingIconClick()}) {
                Icon(trailingIcon, contentDescription = null)
            }
        }},
        keyboardOptions = keyboardOptions,
        singleLine = true,
        visualTransformation = visualTransformation,
        modifier = modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth()
    )
}