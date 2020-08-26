package com.kovalenko.weatherforecast.features.forecast.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kovalenko.weatherforecast.databinding.ItemCityBinding
import com.kovalenko.weatherforecast.features.forecast.model.domain.City

class CityListAdapter(private val callback: CityClick)
    : ListAdapter<City, CityListAdapter.CityViewHolder>(CityDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CityViewHolder(private val viewDataBinding: ItemCityBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(item: City) {
            viewDataBinding.apply {
                city = item
                executePendingBindings()
                root.setOnClickListener {
                    callback.onClick(this, city!!)
                }
            }
        }
    }
}

class CityDiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem
}
