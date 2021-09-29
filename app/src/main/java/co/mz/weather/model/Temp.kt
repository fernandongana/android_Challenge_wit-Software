package co.mz.weather.model

class Temp (
    val coord: Coord,
    val main: Main,
    val wind: Wind,
    val weather: List<Weather>,
    val name: String,
    val dt: Int,
    val sys : Sys,
    val visibility: Double
    )

data class Coord(
    val lat: String,
    val lon: String
)

data class Main (
    val temp: String,
    val feels_like: String,
    val temp_min: String,
    val temp_max: String,
    val humidity: String,
)

data class Sys (
    val country: String,
)

data class Weather (
    val main: String,
    val description: String,
    val icon: String
)

data class Wind (
    val speed: String
)

data class City (
    val name: String,
)

