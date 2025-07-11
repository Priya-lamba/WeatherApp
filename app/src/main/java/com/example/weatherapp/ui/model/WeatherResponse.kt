package com.example.weatherapp.ui.model

data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val pressure: Int,
    val humidity: Int
)
data class Wind(
    val speed: Double
)
