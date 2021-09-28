package co.mz.weather.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.mz.weather.di.API_KEY
import co.mz.weather.di.MyApplication
import co.mz.weather.di.RetroServiceInterface
import co.mz.weather.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherViewModel(application: Application): AndroidViewModel(application){

    @Inject
    lateinit var rService: RetroServiceInterface

    private var weatherLiveData: MutableLiveData<Weather>


    init {
        (application as MyApplication).getRetroComponent().inject(this)
        weatherLiveData = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<Weather>{
        return weatherLiveData
    }

    fun makeApiCall(city: String){
        val call: Call<Weather>? = rService.getDataFromApi(city, "metric",API_KEY)

        call?.enqueue(object : Callback<Weather>{
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if(response.isSuccessful){
                    weatherLiveData.postValue(response.body())
                }else{
                    Log.e(ContentValues.TAG, response.toString())
                    weatherLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                weatherLiveData.postValue(null)
            }

        })
    }

}