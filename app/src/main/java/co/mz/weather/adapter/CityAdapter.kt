package co.mz.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.mz.weather.databinding.RecyclerViewCityRowBinding
import co.mz.weather.model.City

class CityAdapter(private val listener: (String) -> Unit): RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var listCities = mutableListOf<City>()

    class CityViewHolder(val binding : RecyclerViewCityRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(RecyclerViewCityRowBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.binding.textViewCity.text = listCities[position].name

        holder.itemView.setOnClickListener {
            listener(listCities[position].name)
        }
    }

    override fun getItemCount(): Int {
        return if(listCities == null){
            0
        }else{
            listCities.size
        }
    }

    fun addCity(city: City){
        if(!listCities.contains(city)){
            listCities.add(city)
        }
        notifyDataSetChanged()
    }
}