package com.bichi.weather.repo

import com.bichi.weather.models.Weather
import com.bichi.weather.network.WeatherService
import io.reactivex.Observable

class WeatherRepo(private val weatherService: WeatherService) {

    fun getWeatherData(params:Map<String,String>): Observable<Weather> {
        return weatherService.getWeatherData(params)
    }
}