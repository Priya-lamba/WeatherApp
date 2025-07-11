package com.example.weatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel, apiKey: String) {
    var city by remember { mutableStateOf("Delhi") }

    val data = viewModel.weatherData
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val weatherType = data?.weather?.get(0)?.main?.lowercase() ?: ""

    val backgroundBrush = when {
        "cloud" in weatherType -> Brush.verticalGradient(listOf(Color.Gray, Color.DarkGray))
        "rain" in weatherType -> Brush.verticalGradient(listOf(Color(0xFF3F51B5), Color(0xFF1A237E)))
        "clear" in weatherType -> Brush.verticalGradient(listOf(Color(0xFF2196F3), Color(0xFF90CAF9)))
        else -> Brush.verticalGradient(listOf(Color.LightGray, Color.White))
    }

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet && data != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Humidity: ${data.main.humidity}%", fontSize = 16.sp)
                Text("Pressure: ${data.main.pressure} hPa", fontSize = 16.sp)
                Text("Wind Speed: ${data.wind.speed} m/s", fontSize = 16.sp)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter City") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = { viewModel.fetchWeather(city, apiKey) },
                shape = RoundedCornerShape(30.dp),
                colors = buttonColors(containerColor = Color(0xFF1565C0)),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Get Weather", fontWeight = FontWeight.Bold)
            }

            when {
                viewModel.isLoading -> {
                    CircularProgressIndicator(color = Color.White)
                }

                viewModel.error != null -> {
                    Text(
                        text = viewModel.error ?: "",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                data != null -> {
                    val iconUrl = "https://openweathermap.org/img/wn/${data.weather[0].icon}@4x.png"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("City: ${data.name}", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text("Temperature: ${data.main.temp}°C", color = Color.White, fontSize = 18.sp)
                            Text("Weather: ${data.weather[0].main}", color = Color.White, fontSize = 18.sp)

                            Spacer(modifier = Modifier.height(10.dp))

                            Image(
                                painter = rememberAsyncImagePainter(iconUrl),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = {
                                showSheet = true
                                scope.launch { sheetState.show() }
                            }) {
                                Text("More Details")
                            }
                        }
                    }
                }
            }
        }
    }
}
