package com.blazek10.racelog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.blazek10.racelog.ui.controlpoint.ControlPointScreen
import com.blazek10.racelog.ui.theme.RaceLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaceLogTheme {
                //StartScreen(
                //AthleteListScreen(
                //AthleteInfoScreen(
                //ControlPointLoginScreen(
                ControlPointScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}