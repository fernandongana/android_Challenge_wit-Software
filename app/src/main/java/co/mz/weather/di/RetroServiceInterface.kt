package co.mz.weather.di

import co.mz.weather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInterface {

    @GET("weather")
    fun getDataFromApi(@Query(value = "q")query: String, @Query(value = "units")units:String,
                       @Query(value = "APPID")appId: String): Call<Weather>?
}