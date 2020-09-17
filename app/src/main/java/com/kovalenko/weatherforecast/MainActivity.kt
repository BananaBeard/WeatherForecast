package com.kovalenko.weatherforecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kovalenko.weatherforecast.view.clearLightStatusBar
import com.kovalenko.weatherforecast.view.isDarkModeEnabled
import com.kovalenko.weatherforecast.view.setLightStatusBar
import com.kovalenko.weatherforecast.view.setupFullscreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFullscreen(main_root)
        setupNavigation()
    }

    override fun onResume() {
        super.onResume()

        if (isDarkModeEnabled()) {
            clearLightStatusBar(main_root)
        } else {
            setLightStatusBar(main_root)
        }
    }

    private fun setupNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHost.navController
        navigation_main.setupWithNavController(navController)
        navigation_main.setOnNavigationItemReselectedListener {
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val darkMode = isDarkModeEnabled()

            when (destination.id) {
                R.id.placePickerFragment -> {
                    setLightStatusBar(main_root)
                }
                else -> {
                    if (darkMode) {
                        clearLightStatusBar(main_root)
                    } else {
                        setLightStatusBar(main_root)
                    }
                }
            }
        }
    }
}
