package com.kovalenko.weatherforecast.features.forecast.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kovalenko.weatherforecast.databinding.ItemDailyForecastBinding
import com.kovalenko.weatherforecast.features.forecast.model.domain.DailyForecast

class ForecastListAdapter :
        ListAdapter<DailyForecast, ForecastListAdapter.ForecastViewHolder>(ForecastDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemDailyForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ForecastViewHolder(private val viewDataBinding: ItemDailyForecastBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(item: DailyForecast) {
            viewDataBinding.apply {
                forecast = item
                executePendingBindings()
            }
        }
    }
}

class ForecastDiffCallback : DiffUtil.ItemCallback<DailyForecast>() {
    override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast) =
            oldItem.time == newItem.time

    override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast) =
            oldItem == newItem
}
