package com.malopieds.simpleweather.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ElementsCurrent(item: String, value: String){

    val colors = MaterialTheme.colorScheme

    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = item,
            color = colors.onBackground,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = value,
            color = colors.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}