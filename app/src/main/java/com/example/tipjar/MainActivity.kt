package com.example.tipjar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tipjar.ui.home.HomeScreen
import com.example.tipjar.ui.theme.TipJarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipJarTheme {
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
        //composable("history") { HomeScreen(navController) }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    TipJarTheme {
        TipJapApp()
    }
}