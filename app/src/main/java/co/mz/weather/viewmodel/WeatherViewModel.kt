package co.mz.weather.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.mz.weather.di.*
import co.mz.weather.model.Forecast
import co.mz.weather.model.Temp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var retroService: RetroServiceInterface

    private var tempLiveData: MutableLiveData<Temp>
    private var forecastLiveData: MutableLiveData<Forecast>


    init {
        (application as MyApplication).getRetroComponent().inject(this)
        tempLiveData = MutableLiveData()
        forecastLiveData = MutableLiveData()
    }

    fun getTempLiveDataObserver(): MutableLiveData<Temp> {
        return tempLiveData
    }

    fun getForecastLiveDataObserver(): MutableLiveData<Forecast> {
        return forecastLiveData
    }

    fun getCurrentWeather(city: String) {
        val call: Call<Temp>? = retroService.getCurrentWeather(city, Units, Lang, API_KEY)

        call?.enqueue(object : Callback<Temp> {
            override fun onResponse(call: Call<Temp>, response: Response<Temp>) {
                if (response.isSuccessful) {
                    tempLiveData.postValue(response.body())
                } else {
                    tempLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Temp>, t: Throwable) {
                tempLiveData.postValue(null)
            }
        })
    }

    fun getForecastWeather(lat: String, lon: String) {
        val call: Call<Forecast>? = retroService.getForecastWeather(lat, lon, Units, Lang, API_KEY)

        call?.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    forecastLiveData.postValue(response.body())
                } else {
                    forecastLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                forecastLiveData.postValue(null)
            }
        })
    }

}