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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malopieds.simpleweather.enums.WeatherIcon
import java.text.SimpleDateFormat
import java.util.*
import com.malopieds.simpleweather.R

@SuppressLint("SimpleDateFormat")
@Composable
fun HourShow(ForecastHour: Hourly,currentBtn :String){

    val colors = MaterialTheme.colorScheme

    Column(modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(text = SimpleDateFormat("HH:mm").format(Date((ForecastHour.dt).toLong()*1000).time).toString(),
                color = colors.onBackground,
                fontSize = 20.sp)
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(text = SimpleDateFormat("dd-MM").format(Date((ForecastHour.dt).toLong()*1000).time).toString(),
                color = colors.onBackground,
                fontSize = 15.sp)
        }
        when (currentBtn) {
            "temp" -> Temp(ForecastHour)
            "wind" -> Wind(ForecastHour)
            "uv" -> Uv(ForecastHour)
            "prec" -> Precipitation(ForecastHour)
        }

    }
}

@Composable
fun Temp(ForecastHour: Hourly){

    val colors = MaterialTheme.colorScheme

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = WeatherIcon(ForecastHour.weather[0].icon)),
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
            text = Math.round(ForecastHour.temp).toString() + "°",
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
                Math.round(ForecastHour.pop*100).toString() + " %"
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
fun Wind(ForecastHour: Hourly){

    val colors = MaterialTheme.colorScheme

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_up_outline),
            contentDescription = null,
            Modifier
                .size(40.dp)
                .rotate(180f + ForecastHour.wind_deg.toFloat()),
            colorFilter = ColorFilter.tint(colors.inverseSurface),
        )
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp)
        .height(60.dp),
        verticalArrangement = Arrangement.Bottom,) {
        Box(
            modifier = Modifier
                .height((ForecastHour.wind_speed * 6).toInt().dp)
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
        Text(text = ForecastHour.wind_speed.toString(),
            color = colors.onBackground,
            fontSize = 18.sp
        )
    }
}

@Composable
fun Uv(ForecastHour: Hourly){

    val colors = MaterialTheme.colorScheme

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp)
        .height(100.dp),
        verticalArrangement = Arrangement.Bottom,) {
        Box(
            modifier = Modifier
                .height((ForecastHour.uvi * 10f).toInt().dp)
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
        Text(text = ForecastHour.uvi.toString(),
            color = colors.onBackground,
            fontSize = 18.sp
        )
    }
}

@Composable
fun Precipitation(ForecastHour: Hourly){

    val colors = MaterialTheme.colorScheme

    val rain: Double= try {
        ForecastHour.rain.`1h`
    }catch (e: Exception){
        0.0
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = WeatherIcon(ForecastHour.weather[0].icon)),
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