package com.weatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weatherapp.R
import com.weatherapp.ui.theme.Purple40
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.weather_screen.WeatherScreen
import com.weatherapp.ui.weather_screen.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .paint(painterResource(R.drawable.bg_night))
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.WeatherScreen.route,
                        modifier = Modifier.fillMaxSize().background(color = Purple40)
                    ) {
                        composable(route = Screen.WeatherScreen.route) {
                            val viewModel: WeatherViewModel by viewModels()
                            WeatherScreen(viewModel._state.value, viewModel::dismissError)
                        }
                    }
                }
            }
        }
    }
}