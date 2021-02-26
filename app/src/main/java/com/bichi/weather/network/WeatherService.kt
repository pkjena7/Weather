package com.bichi.weather.network

import com.bichi.weather.models.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {
    @GET("weather")
    fun getWeatherData(@QueryMap paramsMap:Map<String, String>):Observable<Weather>
}