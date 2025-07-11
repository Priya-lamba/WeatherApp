package com.example.weatherapp.ui

import com.example.weatherapp.ui.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query



interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}
