package com.malopieds.simpleweather.utils

fun PrecipitationHour(ForecastMinuteList: List<Minutely>): String {
    var nextRain = "No precipitation within an hour"
    if (ForecastMinuteList[0].precipitation > 0){
        var i = 0
        var found = false
        while (i < 60 && !found){
            if(ForecastMinuteList[i].precipitation > 0){
                found = true
                nextRain = "There will be precipitation in ${i+1} minutes"
            }
            i++
        }
    }else {
        var i = 0
        var found = false
        while (i < 60 && !found){
            if(ForecastMinuteList[i].precipitation < 0){
                found = true
                nextRain = "The precipitation will end in ${i+1} minutes"
            }
            i++
        }
    }
    return nextRain
}