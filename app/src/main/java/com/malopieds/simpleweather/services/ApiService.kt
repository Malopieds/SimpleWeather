package com.malopieds.simpleweather.services

import com.malopieds.simpleweather.utils.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


interface APIServiceWeather {
    @GET("/data/2.5/weather?appid=1194375c4d3ac86df88d470f4ebbc7be&units=metric")
    suspend fun getWeather(
        @Query("lon") lon: String,
        @Query("lat") lat: String
    ): WeatherArray
    @GET("/data/2.5/onecall?appid=1194375c4d3ac86df88d470f4ebbc7be&units=metric")
    suspend fun getForecast(
        @Query("lon") lon: String,
        @Query("lat") lat: String
    ): ForecastArray
    @GET("/data/2.5/forecast?appid=1194375c4d3ac86df88d470f4ebbc7be&units=metric")
    suspend fun  getHoursForecast(
        @Query("lon") lon: String,
        @Query("lat") lat: String
    ): HoursForecast

    companion object {
        val BASE_URL = "https://api.openweathermap.org/"

        var apiService: APIServiceWeather? = null
        fun getInstance(): APIServiceWeather {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIServiceWeather::class.java)
            }
            return apiService!!
        }
    }
}

interface APIServiceGeoCoding {
    @GET("/geo/1.0/direct?limit=5&appid=1194375c4d3ac86df88d470f4ebbc7be")
    suspend fun getGeocode(
        @Query("q") q: String? = "London",
    ): ArrayList<GeoCodingItem>

    companion object {
        val BASE_URL = "https://api.openweathermap.org/"

        var apiService: APIServiceGeoCoding? = null
        fun getInstance(): APIServiceGeoCoding {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIServiceGeoCoding::class.java)
            }
            return apiService!!
        }
    }
}
