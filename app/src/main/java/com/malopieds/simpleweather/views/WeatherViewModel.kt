package com.malopieds.simpleweather.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malopieds.simpleweather.services.APIServiceWeather
import com.malopieds.simpleweather.utils.AllWeather
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weather = mutableStateListOf<AllWeather>()
    var errorMessage: String by mutableStateOf("")
    val weather: List<AllWeather>
        get() = _weather

    fun getWeather(q: String, lon: String, lat: String) {
        viewModelScope.launch {
            val apiServiceWeather = APIServiceWeather.getInstance()
            try {
                _weather.clear()
                _weather.addAll(listOf(AllWeather(apiServiceWeather.getWeather(lon = lon, lat = lat), apiServiceWeather.getForecast(lon = lon, lat = lat), apiServiceWeather.getHoursForecast(lon = lon, lat = lat))))
            }  catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}