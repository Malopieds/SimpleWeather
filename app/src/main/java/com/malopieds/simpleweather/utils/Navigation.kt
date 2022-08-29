package com.malopieds.simpleweather.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.malopieds.simpleweather.screens.HomeScreen
import com.malopieds.simpleweather.screens.SearchScreen
import com.malopieds.simpleweather.screens.TestScreen
import com.malopieds.simpleweather.views.WeatherViewModel
import kotlinx.coroutines.flow.map



@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun Navigation(){

    val CITY = stringPreferencesKey("city")
    val LAT = stringPreferencesKey("lat")
    val LON = stringPreferencesKey("lon")
    val context = LocalContext.current

    var city: String = ""
    var lat: String = ""
    var lon: String = ""

    /*city = context.dataStoreCity.data.map { preferences ->
        preferences[CITY] ?: "Paris"
    }.collectAsState(initial = "").value*/
    /*lat = context.dataStoreCity.data.map { preferences ->
        preferences[LAT] ?: "48.8588"
    }.collectAsState(initial = "").value
    lon = context.dataStoreCity.data.map { preferences ->
        preferences[LON] ?: "2.3200"
    }.collectAsState(initial = "").value*/
    
    val vm = WeatherViewModel()

    //val vm2 = GeoCodeViewModel()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(vm = vm, navController = navController, city = city) }
        composable("search") { SearchScreen(navController = navController) }
        //composable("search") { TestScreen() }
    }

}