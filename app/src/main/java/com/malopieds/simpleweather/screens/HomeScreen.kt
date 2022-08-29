package com.malopieds.simpleweather.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.malopieds.simpleweather.R
import com.malopieds.simpleweather.enums.WeatherIcon
import com.malopieds.simpleweather.ui.theme.DarkColorPalette
import com.malopieds.simpleweather.ui.theme.SimpleWeatherTheme
import com.malopieds.simpleweather.utils.*
import com.malopieds.simpleweather.views.WeatherViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private val Context.dataStore by preferencesDataStore("USER_DATA")


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@SuppressLint("FlowOperatorInvokedInComposition", "SimpleDateFormat")
@Composable
fun HomeScreen(vm: WeatherViewModel, navController: NavHostController, city: String) {

    //val TEST = stringPreferencesKey("test")

    val context = LocalContext.current

    val city = runBlocking { DataStoreManager(context).getDataStoreCity().first() }
    //val city = oui.collectAsState(initial = city("Saint-Denis-d'0léron", "", "")).value

    LaunchedEffect(Unit, block = {
        vm.getWeather(city.name, city.lon, city.lat)
    })

    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val lastUpdate = remember { mutableStateOf("") }

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sdf.timeZone = TimeZone.getTimeZone("Europe/Paris")

    val colors = MaterialTheme.colorScheme

    /*lastUpdate.value = context.dataStore.data.map { preferences ->
            preferences[TEST] ?: LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        }.collectAsState(initial = "").value*/

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            isRefreshing = true
        },
    ) {
        Scaffold(
            content = {
                if (vm.errorMessage.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFadadff)) //For background color according to icon of weather
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxHeight()) {
                            items(vm.weather) { all ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text(
                                        all.weather.name,
                                        fontSize = 20.sp,
                                        color = colors.inverseSurface
                                    )
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_search_outline),
                                            contentDescription = null,
                                            Modifier
                                                .size(25.dp)
                                                .clickable {
                                                    navController.navigate("search")
                                                },
                                            colorFilter = ColorFilter.tint(colors.inverseSurface)
                                        )
                                        Spacer(modifier = Modifier.width(25.dp))
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_settings_outline),
                                            contentDescription = null,
                                            Modifier.size(25.dp),
                                            colorFilter = ColorFilter.tint(colors.inverseSurface)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(50.dp))
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        Image(
                                            painter = painterResource(id = WeatherIcon(all.weather.weather[0].icon)),
                                            contentDescription = null,
                                            Modifier.size(150.dp),
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        Text(
                                            text = Math.round(all.weather.main.temp)
                                                .toString() + "°",
                                            fontSize = 100.sp,
                                            color = colors.inverseSurface
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        Text(
                                            text = all.weather.weather[0].description,
                                            fontSize = 18.sp,
                                            color = colors.inverseSurface
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(15.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                    ) {
                                        Text(
                                            text = "Min " + Math.round(all.forecast.daily[0].temp.min)
                                                .toString() + "°",
                                            fontSize = 18.sp,
                                            color = colors.inverseSurface
                                        )
                                        Text(
                                            text = "Max " + Math.round(all.forecast.daily[0].temp.max)
                                                .toString() + "°",
                                            fontSize = 18.sp,
                                            color = colors.inverseSurface
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(50.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp, start = 12.dp, end = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(MaterialTheme.colorScheme.background)

                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(10.dp),
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "Last fetched at ${lastUpdate.value}",
                                                        color = colors.onBackground,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(10.dp),
                                                    horizontalArrangement = Arrangement.SpaceAround
                                                ) {
                                                    ElementsCurrent(
                                                        item = "Feels like",
                                                        value = Math.round(all.weather.main.feels_like)
                                                            .toString() + "°"
                                                    )
                                                    ElementsCurrent(
                                                        item = "Sunrise",
                                                        value = SimpleDateFormat("HH:mm").format(
                                                            Date((all.weather.sys.sunrise).toLong() * 1000).time
                                                        ).toString()
                                                    )
                                                    ElementsCurrent(
                                                        item = "Sunset",
                                                        value = SimpleDateFormat("HH:mm").format(
                                                            Date((all.weather.sys.sunset).toLong() * 1000).time
                                                        ).toString()
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(10.dp),
                                                    horizontalArrangement = Arrangement.SpaceAround
                                                ) {

                                                    ElementsCurrent(
                                                        item = "Wind speed",
                                                        value = all.weather.wind.speed.toString() + " m/s"
                                                    )
                                                    ElementsCurrent(
                                                        item = "Humidity",
                                                        value = all.weather.main.humidity.toString() + "%"
                                                    )
                                                    ElementsCurrent(
                                                        item = "Pressure",
                                                        value = all.weather.main.pressure.toString() + " hPa"
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(top = 10.dp, bottom = 20.dp),
                                                    horizontalArrangement = Arrangement.SpaceAround
                                                ) {

                                                    ElementsCurrent(
                                                        item = "Clouds",
                                                        value = all.weather.clouds.all.toString() + "%"
                                                    )
                                                    ElementsCurrent(
                                                        item = "UV index",
                                                        value = all.forecast.current.uvi.toString()
                                                    )
                                                    ElementsCurrent(
                                                        item = "Visibility",
                                                        value = (all.forecast.current.visibility / 1000).toString() + " km"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp, start = 12.dp, end = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(MaterialTheme.colorScheme.background)
                                        ) {
                                            HourlyForecast(Forecast = all.forecast)
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp, start = 12.dp, end = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(MaterialTheme.colorScheme.background)
                                        ) {
                                            DailyForecast(
                                                Forecast = all.forecast,
                                                HoursForecast = all.hoursForecast
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text(vm.errorMessage)
                }
            }
        )
    }
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(500L)
            vm.getWeather(city.name, city.lon, city.lat)
            lastUpdate.value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            /*context.dataStore.edit { preferences ->
                preferences[TEST] = lastUpdate.value //TODO remettre xDD
            }*/
            isRefreshing = false
        }
    }
}


