package co.mz.weather

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.mz.weather.adapter.ForeCastAdapter
import co.mz.weather.databinding.ActivityDetailsBinding
import co.mz.weather.di.iconsBaseUrl
import co.mz.weather.model.Temp
import co.mz.weather.viewmodel.WeatherViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private var forecastAdapter = ForeCastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detalhes"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.detailsLayout.visibility = View.GONE
        initForecastRecyclerView()
        val city = intent.getStringExtra("city").toString()
        initViewModel(city)
    }

    private fun bindTemp(temperature: Temp){
        val visibility = temperature.visibility / 1000
        binding.textViewDate.text = getDayOfWeek(temperature.dt)
        binding.textViewTemp.text = temperature.main.temp + " \u2103"
        binding.textViewMinTemp.text = temperature.main.temp_min + " \u00B0"
        binding.textViewMaxTemp.text = temperature.main.temp_max + " \u00B0"
        binding.textViewTempDescription.text = temperature.weather[0].description
        binding.textViewLocation.text = temperature.name + ", "+temperature.sys.country
        binding.textViewWind.text = temperature.wind.speed + " m/s"
        binding.textViewHumidity.text = temperature.main.humidity + " %"
        binding.textViewVisibility.text = visibility.toString() + " km"
        Glide.with(this /* context */).load(iconsBaseUrl+temperature.weather[0].icon+".png").diskCacheStrategy(
            DiskCacheStrategy.ALL).into(binding.iconImage).waitForLayout()
    }

    private fun getDayOfWeek(timestamp: Long): String {
        return SimpleDateFormat("EEEE", Locale.forLanguageTag("pt")).format(timestamp * 1000)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel(city: String){
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.getCurrentWeather(city)
        weatherViewModel.getTempLiveDataObserver().observe(this,{
            if(it != null){
                bindTemp(it)
                initForecast(it.coord.lat, it.coord.lon)
            }else{
                Toast.makeText(this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initForecast(lat: String, lon:String){
        weatherViewModel.getForecastWeather(lat, lon)
        weatherViewModel.getForecastLiveDataObserver().observe(this,{
            if(it != null){
                binding.loading.visibility = View.GONE
                binding.detailsLayout.visibility = View.VISIBLE

                for (forecast in it.daily){
                    forecastAdapter.addForecast(forecast)
                    Log.e(ContentValues.TAG, "forecast day: ${forecast.temp.day}")
                }
            }else{
                binding.loading.visibility = View.VISIBLE
                Toast.makeText(this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initForecastRecyclerView(){
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewForecast.adapter = forecastAdapter
        binding.recyclerViewForecast.layoutManager = layoutManager
    }
}