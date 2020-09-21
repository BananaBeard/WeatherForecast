package com.kovalenko.weatherforecast

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.kovalenko.weatherforecast.view.clearLightStatusBar
import com.kovalenko.weatherforecast.view.isDarkModeEnabled
import com.kovalenko.weatherforecast.view.setLightStatusBar
import com.kovalenko.weatherforecast.view.setupFullscreen
import com.kovalenko.weatherforecast.view.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFullscreen(main_root)
        setupBottomNavigation()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigation()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val liveData = navigation_main.setupWithNavController(
            navGraphIds = listOf(R.navigation.graph_forecast, R.navigation.graph_picker),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host,
            intent = intent
        )
        currentNavController = liveData


        currentNavController!!.observe(this, { controller ->
            setUiMode(controller.currentDestination?.id)
        })
    }

    private fun setUiMode(id: Int?) {
        if (id == R.id.placePickerFragment) {
            setLightStatusBar(main_root)
        } else {
            if (isDarkModeEnabled()) {
                clearLightStatusBar(main_root)
            } else {
                setLightStatusBar(main_root)
            }
        }
    }
}
