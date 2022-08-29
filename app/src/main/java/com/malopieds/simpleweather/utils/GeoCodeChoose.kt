package com.malopieds.simpleweather.utils

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
fun GeoCodeChoose(item: GeoCodingItem, navController: NavController){

    val context = LocalContext.current

    val colors = MaterialTheme.colorScheme

    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                coroutineScope.launch {
                    navController.popBackStack()
                    store(context = context, item)
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)) {
                Text(text = item.name, color = colors.onBackground)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)) {
                Text(text = item.country + " , " + item.state, color = colors.onBackground)
            }
        }
    }
}

suspend fun store(context: Context, item: GeoCodingItem){
    DataStoreManager(context).saveDataStoreCity(item.name, item.lat.toString(), item.lon.toString())
}