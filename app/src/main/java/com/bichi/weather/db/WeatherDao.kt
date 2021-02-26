package com.bichi.weather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bichi.weather.models.Weather
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface WeatherDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(data: Weather):Single<Long>

    @Query("select * from weather")
    fun getData():LiveData<List<Weather>>

    @Query("delete from weather")
    suspend fun deleteAll()
}