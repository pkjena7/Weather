package com.bichi.weather.repo

import androidx.lifecycle.LiveData
import com.bichi.weather.db.WeatherDao
import com.bichi.weather.models.Weather
import io.reactivex.Completable
import io.reactivex.Single

class DatabaseRepo(private val weatherDao: WeatherDao) {

    fun getWeatherData(): LiveData<List<Weather>> {
      return weatherDao.getData()
    }

    fun insert(weather: Weather): Single<Long> {
        return weatherDao.insertWeather(weather)
    }

    suspend fun deleteAll(){
        weatherDao.deleteAll()
    }
}