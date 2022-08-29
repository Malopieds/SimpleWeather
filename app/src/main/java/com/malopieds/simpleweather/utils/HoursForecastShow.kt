package com.malopieds.simpleweather.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malopieds.simpleweather.R
import com.malopieds.simpleweather.enums.WeatherIcon
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun HoursForecastShow(Hours: HourForecast, currentBtn: String, currentDay: Int){

    val colors = MaterialTheme.colorScheme

    if (SimpleDateFormat("dd:MM").format(Date((Hours.dt).toLong()*1000).time) == SimpleDateFormat("dd:MM").format(Date((currentDay).toLong()*1000).time)) {
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = SimpleDateFormat("HH:mm").format(Date((Hours.dt).toLong() * 1000).time)
                        .toString(),
                    color = colors.onBackground,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = SimpleDateFormat("dd-MM").format(Date((Hours.dt).toLong() * 1000).time)
                        .toString(),
                    color = colors.onBackground,
                    fontSize = 15.sp
                )
            }
            when (currentBtn) {
                "temp" -> Temp(Hours)
                "wind" -> Wind(Hours)
                "prec" -> Precipitation(Hours)
            }

        }
    }
}

@Composable
fun Temp(Hours: HourForecast){

    val colors = MaterialTheme.colorScheme

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = WeatherIcon(Hours.weather[0].icon)),
            contentDescription = null,
            Modifier.size(40.dp),
        )
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp)
        .height(60.dp),
        verticalArrangement = Arrangement.Center,) {
        Text(
            text = Math.round(Hours.main.temp).toString() + "°",
            color = colors.onBackground,
            fontSize = 18.sp
        )
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = try {
                Math.round(Hours.pop*100).toString() + " %"
            }
            catch (e: Exception){
                "0 %"
            },
            color = colors.onBackground,
            fontSize = 18.sp
        )
    }
}

@Composable
fun Wind(Hours: HourForecast){

    val colors = MaterialTheme.colorScheme

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_up_outline),
            contentDescription = null,
            Modifier
                .size(40.dp)
                .rotate(180f + Hours.wind.deg.toFloat()),
        )
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp)
        .height(60.dp),
        verticalArrangement = Arrangement.Bottom,) {
        Box(
            modifier = Modifier
                .height((Hours.wind.speed * 6).toInt().dp)
                .width(5.dp)
                .fillMaxWidth()
                .background(color = Color.Green)
                .clip(RoundedCornerShape(16.dp))
        )
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center) {
        Text(text = Hours.wind.speed.toString(),
            color = colors.onBackground,
            fontSize = 18.sp
        )
    }
}

@Composable
fun Precipitation(Hours: HourForecast){

    val colors = MaterialTheme.colorScheme

    val rain: Double= try {
        Hours.rain.`3h`
    }catch (e: Exception){
        0.0
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = WeatherIcon(Hours.weather[0].icon)),
            contentDescription = null,
            Modifier
                .size(40.dp),
        )
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp)
        .height(60.dp),
        verticalArrangement = Arrangement.Bottom,) {
        Box(
            modifier = Modifier
                .height((rain * 10).toInt().dp)
                .width(5.dp)
                .fillMaxWidth()
                .background(color = Color.Green)
                .clip(RoundedCornerShape(16.dp))
        )
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center) {
        Text(text = rain.toString(),
            color = colors.onBackground,
            fontSize = 18.sp
        )
    }
}