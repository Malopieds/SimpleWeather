package com.malopieds.simpleweather.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.trace
import com.malopieds.simpleweather.utils.DataStoreManager
import com.malopieds.simpleweather.utils.GeoCodingItem
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TestScreen(){

    val context = LocalContext.current
    var accepted by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

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


            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    // Some works that require permission
                    accepted = true
                    Log.d("ExampleScreen","Code requires permission")
                }
                else -> {
                    // Asking for permission
                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
    if (accepted) {
                val locationManger: LocationManager =
                    context.getSystemService(LOCATION_SERVICE) as LocationManager
                val location: Location? =
                    locationManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                Log.d("POS", "lat "+ location?.latitude.toString()+ " lon " + location?.longitude.toString())
        if (location != null) {
            coroutineScope.launch {
                oui(context, location.latitude.toString(), location.longitude.toString())
            }
        }
                Text(text = location?.latitude.toString())
                Text(text = location?.longitude.toString())
    }
}


suspend fun oui(context: Context, lat: String, lon: String){
    DataStoreManager(context).saveDataStoreCity("Paris", lat, lon)
}
