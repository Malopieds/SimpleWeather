package com.malopieds.simpleweather.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HourlyForecast(Forecast: ForecastArray){
    var currentBtn by remember { mutableStateOf("temp") }

    val colors = MaterialTheme.colorScheme

    val primary = MaterialTheme.colorScheme.primary
    val onSecondary = MaterialTheme.colorScheme.onSecondary

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center) {
            Text(text = "Hourly forecast",
                color = colors.onBackground,
                fontSize = 18.sp,
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = PrecipitationHour(Forecast.minutely),
                color = colors.onBackground,
                fontSize = 15.sp)
        }
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)) { // TODO change it to enums
            item {
                Button(onClick = { currentBtn = "temp" }, modifier = Modifier.padding(start = 20.dp), colors = ButtonDefaults.buttonColors(if (currentBtn != "temp") onSecondary else primary)) {
                    Text(text = "Temperature", color = if (currentBtn != "temp") Color.White else MaterialTheme.colorScheme.onPrimary )
                }
                Button(onClick = { currentBtn = "wind" }, modifier = Modifier.padding(start = 20.dp), colors = ButtonDefaults.buttonColors(if (currentBtn != "wind") onSecondary else primary)) {
                    Text(text = "Wind", color = if (currentBtn != "wind") Color.White else MaterialTheme.colorScheme.onPrimary )
                }
                Button(onClick = { currentBtn = "uv" }, modifier = Modifier.padding(start = 20.dp),colors = ButtonDefaults.buttonColors(if (currentBtn != "uv") onSecondary else primary)) {
                    Text(text = "UV index", color = if (currentBtn != "uv") Color.White else MaterialTheme.colorScheme.onPrimary )
                }
                Button(onClick = { currentBtn = "prec" }, modifier = Modifier.padding(start = 20.dp, end = 20.dp),colors = ButtonDefaults.buttonColors(if (currentBtn != "prec") onSecondary else primary)) {
                    Text(text = "Precipitation", color = if (currentBtn != "prec") Color.White else MaterialTheme.colorScheme.onPrimary )
                }
            }
        }
        LazyRow(modifier = Modifier.fillMaxWidth()){
            items(Forecast.hourly) { hour ->
                Spacer(modifier = Modifier.width(20.dp))
                HourShow(ForecastHour = hour, currentBtn = currentBtn)
            }
        }
    }

}