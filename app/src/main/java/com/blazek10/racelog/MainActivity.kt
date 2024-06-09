package com.blazek10.racelog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.blazek10.racelog.ui.AthleteListScreen
import com.blazek10.racelog.ui.StartScreen
import com.blazek10.racelog.ui.theme.RaceLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaceLogTheme {
                //StartScreen(
                AthleteListScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RaceLogTheme {
//        Greeting("Android")
//    }
//}