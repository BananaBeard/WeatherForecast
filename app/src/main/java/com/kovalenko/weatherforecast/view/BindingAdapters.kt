package com.kovalenko.weatherforecast.view

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kovalenko.weatherforecast.R
import com.squareup.picasso.Picasso

@BindingAdapter("latitude", "longitude")
fun latLon(view: TextView, lat: Double, lon: Double) {
    view.apply {
        text = resources.getString(R.string.lat_lon, lat, lon)
    }
}

@BindingAdapter("temperature")
fun temperature(view: TextView, temp: Double) {
    view.apply {
        text = resources.getString(R.string.temperature, temp)
    }
}
@BindingAdapter("feelsLike")
fun feelsLike(view: TextView, temp: Double) {
    view.apply {
       text = resources.getString(R.string.feels_like, temp)
    }
}

@BindingAdapter("humidityWithLabel")
fun humidityWithLabel(view: TextView, humidity: Int) {
    view.apply {
        text = resources.getString(R.string.humidity_with_label, humidity)
    }
}

@BindingAdapter("humidity")
fun humidity(view: TextView, humidity: Int) {
    view.apply {
        text = resources.getString(R.string.humidity, humidity)
    }
}

@BindingAdapter("windSpeed")
fun windSpeed(view: TextView, speed: Double) {
    view.apply {
        text = resources.getString(R.string.wind_speed, speed)
    }
}

@BindingAdapter("weatherIcon")
fun weatherIcon(view: ImageView, icon: String) {
    val string = "https://openweathermap.org/img/wn/$icon@4x.png"
    Picasso.get().load(string).into(view)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("date")
fun forecastDate(view: TextView, date: Long) {
    val dateString = DateFormat.format("EEE, LLL dd", date * 1000).toString()
    view.text = dateString
}
