package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.ui.WeatherScreen // You'll create this file
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {

    // Replace with your actual OpenWeatherMap API key
    private val apiKey = "e98a8d928046d0526e439487d0a75c86"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: WeatherViewModel = viewModel()
                    WeatherScreen(viewModel = viewModel, apiKey = apiKey)
                }
            }
        }
    }
}
