package co.mz.weather.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.mz.weather.di.API_KEY
import co.mz.weather.di.MyApplication
import co.mz.weather.di.RetroServiceInterface
import co.mz.weather.model.Temp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherViewModel(application: Application): AndroidViewModel(application){

    @Inject
    lateinit var rService: RetroServiceInterface

    private var tempLiveData: MutableLiveData<Temp>


    init {
        (application as MyApplication).getRetroComponent().inject(this)
        tempLiveData = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<Temp>{
        return tempLiveData
    }

    fun makeApiCall(city: String){
        val call: Call<Temp>? = rService.getDataFromApi(city, "metric",API_KEY)

        call?.enqueue(object : Callback<Temp>{
            override fun onResponse(call: Call<Temp>, response: Response<Temp>) {
                if(response.isSuccessful){
                    tempLiveData.postValue(response.body())
                }else{
                    Log.e(ContentValues.TAG, response.toString())
                    tempLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Temp>, t: Throwable) {
                tempLiveData.postValue(null)
            }

        })
    }

}