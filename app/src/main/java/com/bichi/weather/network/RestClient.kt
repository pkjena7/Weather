package com.bichi.weather.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//https://api.openweathermap.org/data/2.5/weather?q=Brahmapur,IN&appid=12ca9285c2d4a0d84153f556e98a6268&units=metric
const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
object RestClient {
    var retrofit:Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}