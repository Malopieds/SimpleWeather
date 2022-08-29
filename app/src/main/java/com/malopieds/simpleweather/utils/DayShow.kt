package com.malopieds.simpleweather.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ExpandableListAdapter
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malopieds.simpleweather.enums.WeatherIcon
import org.intellij.lang.annotations.JdkConstants
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun DayShow(ForecastDay: Daily, HoursForecast: HoursForecast) {

    var currentBtn by remember { mutableStateOf("temp") }
    var hidden by remember { mutableStateOf(true) }

    val colors = MaterialTheme.colorScheme

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { hidden = !hidden },
    horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = SimpleDateFormat("dd-MM").format(Date((ForecastDay.dt).toLong()*1000).time).toString(),
            fontSize = 20.sp,
            color = colors.onBackground)
            Row {
                Image(
                    painter = painterResource(id = WeatherIcon(ForecastDay.weather[0].icon)),
                    contentDescription = null,
                    Modifier.size(30.dp),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = try {
                        Math.round(ForecastDay.pop * 100).toString() + " %"
                    } catch (e: Exception) {
                        "0%"
                    },
                    fontSize = 20.sp,
                    color = colors.onBackground
                )
            }

            Text(
                text = Math.round(ForecastDay.temp.min).toString() + "° / " + Math.round(
                    ForecastDay.temp.max
                ).toString() + "°",
                fontSize = 20.sp,
                color = colors.onBackground
            )
    }

    if (!hidden) {
        LazyRow(modifier = Modifier.fillMaxWidth()){ //Instead, maybe for loop ? ( only for the frist 4 elements, then it's only eve, morn, etc...
            items(HoursForecast.list) { Hours ->
                HoursForecastShow(Hours = Hours, currentBtn = currentBtn, currentDay = ForecastDay.dt)
            }
        }
    }

}