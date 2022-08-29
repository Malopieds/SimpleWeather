package com.malopieds.simpleweather.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malopieds.simpleweather.services.APIServiceGeoCoding
import com.malopieds.simpleweather.utils.GeoCoding
import com.malopieds.simpleweather.utils.GeoCodingItem
import kotlinx.coroutines.launch

class GeoCodeViewModel : ViewModel() {
    val _geoCode = mutableStateListOf<GeoCodingItem>()
    var errorMessage: String by mutableStateOf("")
    val geoCode: List<GeoCodingItem>
        get() = _geoCode

    fun getGeoCode(city : String) {
        viewModelScope.launch {
            val apiServiceGeoCoding = APIServiceGeoCoding.getInstance()
            apiServiceGeoCoding.getGeocode(q = city)
            _geoCode.clear()
            try {
                _geoCode.clear()
                _geoCode.addAll(apiServiceGeoCoding.getGeocode(q = city))
            }  catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}