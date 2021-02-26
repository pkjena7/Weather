package com.bichi.weather.di

import android.app.Application
import androidx.room.Room
import com.bichi.weather.R
import com.bichi.weather.db.DB_NAME
import com.bichi.weather.db.Db
import com.bichi.weather.db.WeatherDao
import com.bichi.weather.network.BASE_URL
import com.bichi.weather.network.WeatherService
import com.bichi.weather.repo.DatabaseRepo
import com.bichi.weather.repo.WeatherRepo
import com.bichi.weather.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    fun provideCountriesApi(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
    single { provideCountriesApi(get()) }

}

val networkModule = module {
    fun provideRetrofit(baseUrl: String): Retrofit {

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single {
        val baseUrl = androidContext().getString(R.string.BASE_URL)
        provideRetrofit(baseUrl)
    }
}

val databaseModule = module {

    fun provideDatabase(application: Application): Db {

        return Room.databaseBuilder(application, Db::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    fun provideCountriesDao(database: Db): WeatherDao {
        return  database.getWeatherDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}

val dbRepo = module {

    fun provideDbRepository(weatherDao: WeatherDao): DatabaseRepo {
        return DatabaseRepo(weatherDao)
    }
    single { provideDbRepository(get()) }

}

val weatherRepo = module {

    fun provideWeatherRepo(weatherService: WeatherService): WeatherRepo {
        return WeatherRepo(weatherService)
    }
    single { provideWeatherRepo(get()) }

}

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        MainViewModel(weatherRepo = get(),databaseRepo = get())
    }

}
