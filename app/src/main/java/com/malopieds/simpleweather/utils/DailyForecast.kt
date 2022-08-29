package com.malopieds.simpleweather.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun DailyForecast(Forecast: ForecastArray, HoursForecast: HoursForecast){

    val colors = MaterialTheme.colorScheme

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center) {
            Text(text = "Daily forecast",
                color = colors.onBackground,
                fontSize = 18.sp)
        }
        Column() {
            for (day in Forecast.daily) {
                DayShow(ForecastDay = day, HoursForecast = HoursForecast)
            }
        }
    }
}