package com.malopieds.simpleweather.screens

import android.Manifest
import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.malopieds.simpleweather.R
import com.malopieds.simpleweather.utils.*
import com.malopieds.simpleweather.views.GeoCodeViewModel
import com.malopieds.simpleweather.views.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavController){

    val context = LocalContext.current

    var textFieldValue by remember { mutableStateOf("")}

    val colors = MaterialTheme.colorScheme

    val kc = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var finished by remember { mutableStateOf(false) }

    var vm by remember { mutableStateOf(GeoCodeViewModel()) }


    val coroutineScope = rememberCoroutineScope()


    var accepted by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            accepted = true
            Log.d("ExampleScreen","PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 12.dp, end = 12.dp, top = 12.dp)) {
        Row(modifier = Modifier.fillMaxWidth()){
            BasicTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                keyboardActions = KeyboardActions(
                    onDone = {
                        kc?.hide()
                        focusManager.clearFocus()
                        finished = true
                        vm = GeoCodeViewModel()
                    }
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .background(Color.LightGray, RoundedCornerShape(percent = 30))
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        if (textFieldValue.isEmpty()) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Row {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_search_outline),
                                        contentDescription = null,
                                        Modifier
                                            .size(25.dp),
                                        colorFilter = ColorFilter.tint(colors.inverseSurface)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text("Search for a city")
                                }
                               Row {
                                   Image(
                                       painter = painterResource(id = R.drawable.ic_location_outline),
                                       contentDescription = null,
                                       Modifier
                                           .size(25.dp)
                                           .clickable {
                                               when (PackageManager.PERMISSION_GRANTED) {
                                                   ContextCompat.checkSelfPermission(
                                                       context,
                                                       Manifest.permission.ACCESS_FINE_LOCATION
                                                   ) -> {
                                                       accepted = true
                                                       Log.d("ExampleScreen","Code requires permission")
                                                   }
                                                   else -> {
                                                       launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                                   }
                                               }
                                               if (accepted) {
                                                   val locationManger: LocationManager =
                                                       context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                                                   val location: Location? =
                                                       locationManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                                                   Log.d("POS", "lat "+ location?.latitude.toString()+ " lon " + location?.longitude.toString())
                                                   if (location != null) {
                                                       coroutineScope.launch {
                                                           store(context, location.latitude.toString(), location.longitude.toString())
                                                       }
                                                   }
                                               }
                                           },
                                       colorFilter = ColorFilter.tint(colors.inverseSurface)
                                   )
                               }

                            }
                        }
                        innerTextField()  //<-- Add this
                    }
                }
            )
        }
        if (finished) {

            vm.getGeoCode(textFieldValue)
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(vm.geoCode) { item ->
                        Spacer(modifier = Modifier.height(10.dp))
                        GeoCodeChoose(item = item, navController = navController)
                    }
                }
            }
        }
    }
}

suspend fun store(context: Context, lat: String, lon: String){
    DataStoreManager(context).saveDataStoreCity("Paris", lat, lon)
}
