package co.mz.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.mz.weather.di.listCities
import co.mz.weather.model.City

class CityViewModel: ViewModel() {

    private var cityLiveData: MutableLiveData<List<City>> = MutableLiveData()


    fun getLiveDataObserver(): MutableLiveData<List<City>> {
        return cityLiveData
    }

    fun loadCities(): MutableLiveData<List<City>> {
       // cityLiveData.value = listCities.toList()
        cityLiveData.postValue(listCities.toList())
        return cityLiveData
    }
}