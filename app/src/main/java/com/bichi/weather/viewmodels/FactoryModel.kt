package com.bichi.weather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bichi.weather.db.WeatherDao
import com.bichi.weather.repo.DatabaseRepo
import com.bichi.weather.repo.WeatherRepo

/*
class FactoryModel(
    private val weatherRepo: WeatherRepo,
    private val databaseRepo: DatabaseRepo
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(weatherRepo,databaseRepo) as T
    }
}*/
