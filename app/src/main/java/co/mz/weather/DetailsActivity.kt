package co.mz.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import co.mz.weather.databinding.ActivityDetailsBinding
import co.mz.weather.model.Weather
import co.mz.weather.viewmodel.WeatherViewModel

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detalhes"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.detailsLayout.visibility = View.GONE

        val city = intent.getStringExtra("city").toString()
        initViewModel(city)
    }

    private fun bind(weather: Weather){
        binding.textViewTemp.text = weather.main.temp + " \u2103"
        binding.textViewLocation.text = weather.name + ", "+weather.sys.country
        binding.textViewWind.text = weather.wind.speed + " m/s"
        binding.textViewHumidity.text = weather.main.humidity + " %"
        binding.textViewVisibility.text = weather.visibility.toString()
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
        weatherViewModel.makeApiCall(city)
        weatherViewModel.getLiveDataObserver().observe(this,{
            if(it != null){
                binding.loading.visibility = View.GONE
                binding.detailsLayout.visibility = View.VISIBLE
                bind(it)
            }else{
                binding.loading.visibility = View.VISIBLE
                Toast.makeText(this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
            }
        })
    }
}