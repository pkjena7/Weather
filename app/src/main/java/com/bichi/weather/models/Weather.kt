package com.bichi.weather.models


import androidx.room.*
import com.bichi.weather.Convoter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @Embedded
    val coord: Coord,
    @Embedded
    val main: Main,
    val name: String,
    @Embedded
    val sys: Sys,
    val weather: List<WeatherX>,
    @Embedded
    val wind: Wind
)