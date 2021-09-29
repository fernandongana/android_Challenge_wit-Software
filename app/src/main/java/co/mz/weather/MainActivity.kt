package co.mz.weather

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.mz.weather.adapter.CityAdapter
import co.mz.weather.databinding.ActivityMainBinding
import co.mz.weather.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var cityAdapter = CityAdapter { city ->
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("city", city)
        startActivity(intent)
    }

    private lateinit var cityViewModel: CityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCities.adapter = cityAdapter
        binding.recyclerViewCities.layoutManager = layoutManager
    }

    private fun initViewModel(){
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)
        cityViewModel.loadCities()
        cityViewModel.getLiveDataObserver().observe(this,{
            if(!it.isNullOrEmpty()){
                for (city in it){
                    cityAdapter.addCity(city)
                    Log.e(ContentValues.TAG, "City : ${city.name}")
                }
            }
        })
    }
}