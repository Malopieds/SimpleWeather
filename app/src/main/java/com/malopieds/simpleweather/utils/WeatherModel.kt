package com.malopieds.simpleweather.utils

//weather

data class AllWeather(
    val weather: WeatherArray,
    val forecast: ForecastArray,
    val hoursForecast: HoursForecast
)

data class WeatherArray(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    val rain: Rain,
    val pop: Double
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)


//forecast

data class ForecastArray(
    val alerts: List<Alert>,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)

data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    val sender_name: String,
    val start: Int,
    val tags: List<String>
)

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

data class Daily(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: FeelsLike,
    val humidity: Int,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Rain,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

data class Minutely(
    val dt: Int,
    val precipitation: Double
)

data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)

data class Rain(
    val `1h`: Double,
    val `3h`: Double
)

//5 day 3H forecast

data class HoursForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<HourForecast>,
    val message: Int
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class HourForecast(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

// GeoCoding 
class GeoCoding : ArrayList<GeoCodingItem>()

data class GeoCodingItem(
    val country: String,
    val lat: Double,
    //val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)

data class LocalNames(
    val ab: String,
    val af: String,
    val am: String,
    val an: String,
    val ar: String,
    val ascii: String,
    val ba: String,
    val be: String,
    val bg: String,
    val bn: String,
    val bo: String,
    val br: String,
    val ca: String,
    val co: String,
    val cs: String,
    val cu: String,
    val cv: String,
    val cy: String,
    val de: String,
    val el: String,
    val en: String,
    val eo: String,
    val es: String,
    val et: String,
    val eu: String,
    val fa: String,
    val feature_name: String,
    val fi: String,
    val fr: String,
    val fy: String,
    val ga: String,
    val gd: String,
    val gl: String,
    val gn: String,
    val gu: String,
    val gv: String,
    val he: String,
    val hi: String,
    val ht: String,
    val hu: String,
    val hy: String,
    val `is`: String,
    val `it`: String,
    val ja: String,
    val ka: String,
    val kk: String,
    val kn: String,
    val ko: String,
    val ku: String,
    val kv: String,
    val kw: String,
    val ky: String,
    val li: String,
    val ln: String,
    val lo: String,
    val lt: String,
    val lv: String,
    val mi: String,
    val mk: String,
    val ml: String,
    val mn: String,
    val mr: String,
    val mt: String,
    val my: String,
    val ne: String,
    val nl: String,
    val no: String,
    val oc: String,
    val or: String,
    val os: String,
    val pl: String,
    val ps: String,
    val pt: String,
    val rm: String,
    val ro: String,
    val ru: String,
    val sa: String,
    val sc: String,
    val si: String,
    val sk: String,
    val sl: String,
    val sq: String,
    val sr: String,
    val sv: String,
    val ta: String,
    val te: String,
    val tg: String,
    val th: String,
    val tl: String,
    val tr: String,
    val tt: String,
    val uk: String,
    val ur: String,
    val vi: String,
    val wo: String,
    val yi: String,
    val yo: String,
    val zh: String,
    val zu: String
) // may not use this, because of translation