package co.mz.weather.di

import co.mz.weather.model.City

const val baseUrl = "https://api.openweathermap.org/data/2.5/"
const val iconsBaseUrl = "https://openweathermap.org/img/w/"
const val API_KEY = "a7e3802fc502dcdbdc6a9ef509b6ec6b"
const val Lang = "pt"
const val Units = "metric"
const val DegreesCelcius =" \u2103"
const val Degrees = " \u00B0"
const val Km = " km"
const val Percentage = " %"
const val MeteresPerSecond = " m/s"

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

fun convertDoubletoInt(value: Double): String{
    return String.format("${value.toInt()}")
}