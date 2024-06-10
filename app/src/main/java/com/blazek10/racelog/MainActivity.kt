package com.blazek10.racelog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.blazek10.racelog.ui.racelog.RaceLogApp
import com.blazek10.racelog.ui.theme.RaceLogTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaceLogTheme {
                RaceLogApp()
                FirebaseApp.initializeApp(this)
            }
        }
    }
}