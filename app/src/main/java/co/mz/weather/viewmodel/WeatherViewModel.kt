package co.mz.weather.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.mz.weather.di.API_KEY
import co.mz.weather.di.MyApplication
import co.mz.weather.di.RetroServiceInterface
import co.mz.weather.model.Forecast
import co.mz.weather.model.Temp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var rService: RetroServiceInterface

    private var tempLiveData: MutableLiveData<Temp>
    private var forecatLiveData: MutableLiveData<Forecast>


    init {
        (application as MyApplication).getRetroComponent().inject(this)
        tempLiveData = MutableLiveData()
        forecatLiveData = MutableLiveData()
    }

    fun getTempLiveDataObserver(): MutableLiveData<Temp> {
        return tempLiveData
    }

    fun getForecsatLiveDataObserver(): MutableLiveData<Forecast> {
        return forecatLiveData
    }

    fun getCurrentWeather(city: String) {
        val call: Call<Temp>? = rService.getCurrentWeather(city, "metric", "pt", API_KEY)

        call?.enqueue(object : Callback<Temp> {
            override fun onResponse(call: Call<Temp>, response: Response<Temp>) {
                if (response.isSuccessful) {
                    tempLiveData.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, response.toString())
                    tempLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Temp>, t: Throwable) {
                tempLiveData.postValue(null)
            }
        })
    }

    fun getForecastWeather(lat: String, lon: String) {
        val call: Call<Forecast>? = rService.getForecastWeather(lat, lon, "metric", "pt", API_KEY)

        call?.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    forecatLiveData.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, response.toString())
                    forecatLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                forecatLiveData.postValue(null)
                Log.e(ContentValues.TAG, "Error : ${t.message}")
            }
        })
    }

}