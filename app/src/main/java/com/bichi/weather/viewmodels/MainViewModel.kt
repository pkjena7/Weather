package com.bichi.weather.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bichi.weather.db.WeatherDao
import com.bichi.weather.models.Weather
import com.bichi.weather.repo.DatabaseRepo
import com.bichi.weather.repo.WeatherRepo
import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

const val APP_ID="12ca9285c2d4a0d84153f556e98a6268"
const val UNITS ="metric"
class MainViewModel(
    private val weatherRepo: WeatherRepo,
    private val databaseRepo: DatabaseRepo
) :ViewModel(){
    var weatherData = MutableLiveData<Weather>()

    var compositeDisposable = CompositeDisposable()

    fun getWeatherData(location:String){
        val hashMap = hashMapOf<String,String>()

        hashMap["q"]=location
        hashMap["appid"]= APP_ID
        hashMap["units"]= UNITS

        weatherRepo.getWeatherData(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //weatherData.postValue(it)
                deleteAll()
                Log.d(TAG, "getWeatherData: "+it.toString())
                insertDataInDb(it)
            }, {
                Log.e(TAG, "getWeatherData: ${it.message}",)
            }).let { compositeDisposable.add(it) }
    }

    private fun deleteAll() {
        viewModelScope.launch {
            databaseRepo.deleteAll()
        }
    }

    private fun insertDataInDb(data:Weather){
        databaseRepo.insert(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {l-> Log.d(TAG, "insertDataInDb:${l} ") },
                    {e-> Log.d(TAG, "insertDataInDb: ${e.message}") }
            ).let { compositeDisposable.add(it) }
    }
    fun getDbData(): LiveData<List<Weather>> {
        return databaseRepo.getWeatherData()
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    companion object{
        val TAG :String = MainViewModel::class.java.simpleName
    }
}