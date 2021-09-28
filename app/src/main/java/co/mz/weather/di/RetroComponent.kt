package co.mz.weather.di

import co.mz.weather.viewmodel.WeatherViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {

    fun inject(weatherViewModel: WeatherViewModel)

}