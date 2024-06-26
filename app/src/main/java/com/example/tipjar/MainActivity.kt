package com.example.tipjar

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tipjar.ui.camera.CameraScreen
import com.example.tipjar.ui.history.HistoryScreen
import com.example.tipjar.ui.home.HomeScreen
import com.example.tipjar.ui.theme.TipJarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            TipJarTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TipJapApp()
                }
            }
        }
    }
}

@Composable
fun TipJapApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("history") { HistoryScreen(navController) }
        composable(
            "camera/{timestamp}",
            arguments = listOf(navArgument("timestamp") { type = NavType.LongType })
        ) { CameraScreen(navController) }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    TipJarTheme {
        TipJapApp()
    }
}