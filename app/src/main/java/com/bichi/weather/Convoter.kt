package com.bichi.weather

import androidx.room.TypeConverter
import com.bichi.weather.models.WeatherX
import com.google.gson.Gson

class Convoter {
    @TypeConverter
    fun listToJson(data:List<WeatherX>): String? {
        data.let {
            return Gson().toJson(it)
        }
    }

    @TypeConverter
    fun jsonToList(data:String): List<WeatherX>? {
        val objects = Gson().fromJson(data,Array<WeatherX>::class.java)
        return objects?.toList()
    }
}