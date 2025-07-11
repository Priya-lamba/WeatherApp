package com.example.weatherapp.viewmodel

import com.example.weatherapp.ui.model.WeatherResponse
import com.example.weatherapp.RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class WeatherViewModel : ViewModel() {

    var weatherData by mutableStateOf<WeatherResponse?>(null)
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun fetchWeather(city: String, apiKey: String) {
        isLoading = true
        error = null

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(city, apiKey)
                weatherData = response
            } catch (e: Exception) {
                error = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
