package co.mz.weather.model

data class Forecast(
    val timezone: String,
    val daily: List<DayTemp>,
)

data class DayTemp(
    val dt: Long,
    val temp: TempForecast,
    val weather: List<Weather>,
    val humidity: Int,
    val wind_speed: Double
)

data class TempForecast(
    val day: Double,
    val min: Double,
    val max: Double
)