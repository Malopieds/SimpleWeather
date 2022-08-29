package com.malopieds.simpleweather.utils

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("USER_DATA")

class DataStoreManager(val context: Context) {
    companion object {
        val CITY = stringPreferencesKey("city")
        val LAT = stringPreferencesKey("lat")
        val LON = stringPreferencesKey("lon")
    }

    suspend fun saveDataStoreCity(city: String, lat: String, lon: String){
        context.dataStore.edit { preferences ->
            preferences[CITY] = city
            preferences[LAT] = lat
            preferences[LON] = lon
        }
    }

    fun getDataStoreCity() = context.dataStore.data.map {
        city(
            name = it[DataStoreManager.CITY] ?: "Paris",
            lat = it[DataStoreManager.LAT] ?: "48.8534",
            lon = it[DataStoreManager.LON] ?: "2.3488"
        )
    }
}