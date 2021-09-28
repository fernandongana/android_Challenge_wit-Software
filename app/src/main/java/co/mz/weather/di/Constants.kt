package co.mz.weather.di

import co.mz.weather.model.City

const val baseUrl = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "a7e3802fc502dcdbdc6a9ef509b6ec6b"

val listCities: Array<City> = arrayOf(
    City("Maputo"),
    City("Lisboa"),
    City("Madrid"),
    City("Paris"),
    City("Berlim"),
    City("Copenhaga"),
    City("Roma"),
    City("Londres"),
    City("Dublin"),
    City("Praga"),
    City("Viena"),
)