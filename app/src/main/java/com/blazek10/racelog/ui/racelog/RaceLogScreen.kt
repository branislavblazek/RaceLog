package com.blazek10.racelog.ui.racelog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.blazek10.racelog.R
import com.blazek10.racelog.ui.AthleteInfoScreen
import com.blazek10.racelog.ui.StartScreen
import com.blazek10.racelog.ui.athletelist.AthleteListScreen
import com.blazek10.racelog.ui.controlpoint.ControlPointScreen
import com.blazek10.racelog.ui.controlpointlogin.ControlPointLoginScreen

enum class RaceLogScreen(@StringRes val title: Int) {
    Start(R.string.start_screen_title),
    AthleteList(R.string.athlete_list_screen_title),
    AthleteInfo(R.string.athlete_info_screen_title),
    ControlPointLogin(R.string.control_point_login_screen_title),
    ControlPointInfo(R.string.control_point_info_screen_title),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceLogAppBar(
    currentScreen: RaceLogScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }

        }
    )
}

@Composable
fun RaceLogApp(
    raceLogViewModel: RaceLogViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val raceLogUiState by raceLogViewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RaceLogScreen.valueOf(
        backStackEntry?.destination?.route ?: RaceLogScreen.Start.name)

    Scaffold(
        topBar = {
            RaceLogAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RaceLogScreen.Start.name,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable(route = RaceLogScreen.Start.name) {
                StartScreen(
                    onWatchRaceClicked = {
                        navController.navigate(RaceLogScreen.AthleteList.name) },
                    onControlPointLoginClicked = {
                        navController.navigate(RaceLogScreen.ControlPointLogin.name) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = RaceLogScreen.AthleteList.name) {
                AthleteListScreen(
                    onAthleteClicked = {
                        raceLogViewModel.updateAthleteId(it)
                        navController.navigate(RaceLogScreen.AthleteInfo.name) },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = RaceLogScreen.AthleteInfo.name) {
                AthleteInfoScreen(
                    athleteId = raceLogUiState.selectedAthleteId,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = RaceLogScreen.ControlPointLogin.name) {
                ControlPointLoginScreen(
                    onLogin = {
                        navController.navigate(RaceLogScreen.ControlPointInfo.name) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = RaceLogScreen.ControlPointInfo.name) {
                ControlPointScreen(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

    }
}