package com.malopieds.simpleweather.enums

import com.malopieds.simpleweather.R

fun WeatherIcon (iconString: String): Int {
    return when (iconString) {
        "01d" -> R.drawable.ic_sunny_outline
        "02d" -> R.drawable.ic_partly_sunny_outline
        "03d", "03n" -> R.drawable.ic_cloudy_outline
        "04d", "04n" -> R.drawable.ic_broken_clouds_outline
        "09d","10d", "09n", "10n" -> R.drawable.ic_rainy_outline
        "11d", "11n" -> R.drawable.ic_thunderstorm_outline
        "12d", "12n" -> R.drawable.ic_snow_outline
        "50d", "50n" -> R.drawable.ic_mist
        "01n" -> R.drawable.ic_moon_outline
        "02n" -> R.drawable.ic_cloudy_night_outline
        else -> R.drawable.ic_cloudy_outline
    }
}