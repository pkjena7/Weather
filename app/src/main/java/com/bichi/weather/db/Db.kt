package com.bichi.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bichi.weather.Convoter
import com.bichi.weather.models.Weather

@Database(entities = [Weather::class], version = DB_VERSION)
@TypeConverters(Convoter::class)
abstract class Db : RoomDatabase(){
    abstract fun getWeatherDao():WeatherDao

    companion object{
        @Volatile
        private var databaseInstance : Db ?= null

        fun getDatabaseInstance(mContext: Context):Db =
            databaseInstance ?: synchronized(this){
                databaseInstance ?: buildDatabaseInstance(mContext).also {
                    databaseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, Db::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
const val DB_VERSION = 4
const val DB_NAME = "PersonDataSample.db"