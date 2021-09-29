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

    private fun bindTemp(temp: Temp){
        binding.textViewTemp.text = temp.main.temp + " \u2103"
        binding.textViewMinTemp.text = temp.main.temp_min + " \u00B0"
        binding.textViewMaxTemp.text = temp.main.temp_max + " \u00B0"
        binding.textViewTempDescription.text = temp.weather[0].description
        binding.textViewLocation.text = temp.name + ", "+temp.sys.country
        binding.textViewWind.text = temp.wind.speed + " m/s"
        binding.textViewHumidity.text = temp.main.humidity + " %"
        binding.textViewVisibility.text = temp.visibility.toString()
        Glide.with(this /* context */).load(iconsBaseUrl+temp.weather[0].icon+".png").diskCacheStrategy(
            DiskCacheStrategy.ALL).into(binding.iconImage).waitForLayout()
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
                Log.e(ContentValues.TAG, "lat : ${it.coord.lat}")
            }else{
                Toast.makeText(this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initForecast(lat: String, lon:String){
        weatherViewModel.getForecastWeather(lat, lon)
        weatherViewModel.getForecsatLiveDataObserver().observe(this,{
            if(it != null){
                binding.loading.visibility = View.GONE
                binding.detailsLayout.visibility = View.VISIBLE
                Log.e(ContentValues.TAG, "TimeZone : ${it.timezone}")

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