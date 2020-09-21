package com.kovalenko.weatherforecast.view

import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat


fun AppCompatActivity.isDarkModeEnabled() =
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> {
            false
        }
        Configuration.UI_MODE_NIGHT_YES -> {
            true
        }
        else -> false
    }

/**
 * Using deprecated API until WindowInsetsController got added to AndroidX.
 */
fun AppCompatActivity.setLightStatusBar(view: View) {
    var flags = view.getSystemUiVisibility()
    flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    view.setSystemUiVisibility(flags)
    this.window.statusBarColor = Color.TRANSPARENT
}

/**
 * Using deprecated API until WindowInsetsController got added to AndroidX.
 */
fun AppCompatActivity.clearLightStatusBar(view: View) {
    var flags = view.getSystemUiVisibility()
    flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    view.setSystemUiVisibility(flags)
    this.window.statusBarColor = Color.TRANSPARENT
}

fun AppCompatActivity.setupFullscreen(
    rootView: ViewGroup,
    topView: View? = null,
    bottomView: View? = null
) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->

        topView?.let {
            insets.getInsets(WindowInsetsCompat.Type.systemBars()).top.apply {
                if (this > 0) {
                    it.setMarginTop(this)
                }
            }
        }

        bottomView?.let {
            insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom.apply {
                if (this > 0) {
                    it.setMarginBottom(this)
                }
            }
        }

        insets
    }
}

fun View.setMarginTop(marginTop: Int) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.topMargin = marginTop
    this.layoutParams = layoutParams
}

fun View.setMarginBottom(marginBottom: Int) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.bottomMargin = marginBottom
    this.layoutParams = layoutParams
}
