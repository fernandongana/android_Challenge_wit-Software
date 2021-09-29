package co.mz.weather.di

import co.mz.weather.model.Forecast
import co.mz.weather.model.Temp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInterface {

    @GET("weather")
    fun getCurrentWeather(@Query(value = "q")query: String, @Query(value = "units")units:String,
                          @Query(value = "lang")lang:String,
                          @Query(value = "APPID")appId: String): Call<Temp>?

    @GET("onecall")
    fun getForecastWeather(@Query(value = "lat")lat: String, @Query(value = "lon")lon: String,
                           @Query(value = "units")units:String, @Query(value = "lang")lang:String,
                           @Query(value = "APPID")appId: String): Call<Forecast>?
}