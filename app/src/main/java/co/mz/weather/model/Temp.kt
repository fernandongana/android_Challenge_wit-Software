package co.mz.weather.model

class Temp(
    val coord: Coord,
    val main: Main,
    val wind: Wind,
    val weather: List<Weather>,
    val name: String,
    val dt: Long,
    val sys: Sys,
    val visibility: Int
)

data class Coord(
    val lat: String,
    val lon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: String,
)

data class Sys(
    val country: String,
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: String
)

data class City(
    val name: String,
)

