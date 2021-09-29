package co.mz.weather.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.mz.weather.databinding.RecyclerViewDailyRowBinding
import co.mz.weather.di.iconsBaseUrl
import co.mz.weather.model.DayTemp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.util.*

class ForeCastAdapter : RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder>() {

    private var listForecast = mutableListOf<DayTemp>()

    class ForeCastViewHolder(val binding : RecyclerViewDailyRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastViewHolder {
        return ForeCastViewHolder(
            RecyclerViewDailyRowBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ForeCastViewHolder, position: Int) {
        val date = getDayOfWeek(listForecast[position].dt)

        holder.binding.textViewMinTemp.text = listForecast[position].temp.min
        holder.binding.textViewMaxTemp.text = listForecast[position].temp.max
        holder.binding.textViewDate.text = date
        Log.e(ContentValues.TAG, "Image : ${listForecast[position].weather[0].icon}")
        Glide.with(holder.itemView.context).load(iconsBaseUrl +listForecast[position].weather[0].icon+".png").diskCacheStrategy(
            DiskCacheStrategy.ALL).into(holder.binding.iconImage).waitForLayout()
    }

    private fun getDayOfWeek(timestamp: Long): String {
        return SimpleDateFormat("EEEE", Locale.forLanguageTag("pt")).format(timestamp * 1000)
    }

    override fun getItemCount(): Int {
        return if(listForecast == null){
            0
        }else{
            listForecast.size
        }
    }

    fun addForecast(forecast: DayTemp){
        if(!listForecast.contains(forecast)){
            listForecast.add(forecast)
        }
        notifyDataSetChanged()
    }
}