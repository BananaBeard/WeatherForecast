package com.kovalenko.weatherforecast.features.forecast.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.kovalenko.weatherforecast.R
import com.kovalenko.weatherforecast.databinding.ForecastFragmentBinding
import com.kovalenko.weatherforecast.features.forecast.model.domain.Forecast
import com.kovalenko.weatherforecast.features.forecast.viewmodel.ForecastViewModel
import com.kovalenko.weatherforecast.persistence.Resource
import com.kovalenko.weatherforecast.util.Status
import com.kovalenko.weatherforecast.view.MarginItemDecorator
import com.kovalenko.weatherforecast.view.setMarginTop
import kotlinx.android.synthetic.main.forecast_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : Fragment() {
    private val args by navArgs<ForecastFragmentArgs>()
    private val forecastViewModel by viewModel<ForecastViewModel>()
    private lateinit var viewDataBinding: ForecastFragmentBinding
    private var forecastAdapter: ForecastListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host
            interpolator = FastOutSlowInInterpolator()
            duration = resources.getInteger(R.integer.transition_default_duration).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ForecastFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ForecastFragment.viewLifecycleOwner
            viewmodel = forecastViewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinateMotion()
        setupSubscriptions()
        setupRecycler()
        setupFullscreen()
        forecastViewModel.loadWeather(args.city)
    }

    private fun setupSubscriptions() {
        forecastViewModel.forecast.observe(this.viewLifecycleOwner, Observer(::onForecastUpdate))
    }

    private fun setupRecycler() {
        forecastAdapter = ForecastListAdapter()
        viewDataBinding.recyclerForecast.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = forecastAdapter
            addItemDecoration(
                MarginItemDecorator(
                    resources.getDimension(R.dimen.margin_default).toInt()
                )
            )
        }

        viewDataBinding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                forecastViewModel.loadWeather(args.city)
            }
            setColorSchemeColors(
                resources.getColor(R.color.orange_primary, requireActivity().theme)
            )
            setProgressViewOffset(false, 0, 170)
        }
    }

    private fun setupFullscreen() {
        ViewCompat.setOnApplyWindowInsetsListener(viewDataBinding.root) { v, insets ->
            insets.getInsets(WindowInsetsCompat.Type.systemBars()).top.apply {
                if (this > 0) {
                    viewDataBinding.forecastRoot.setMarginTop(this)
                }
            }
            insets
        }
    }

    private fun onForecastUpdate(forecastResource: Resource<Forecast?>) {
        startPostponedEnterTransition()
        viewLifecycleOwner.lifecycleScope.launch {
            forecastAdapter?.submitList(forecastResource.data?.daily)
            viewDataBinding.recyclerForecast.scheduleLayoutAnimation()
            if (forecastResource.status == Status.ERROR) {
                view?.let {
                    Snackbar.make(it, forecastResource.message.toString(), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.retry)) {
                            forecastViewModel.loadWeather(args.city)
                        }
                        .setAnchorView(requireActivity().findViewById(R.id.navigation_main))
                        .show()
                }
            }
        }
    }

    private fun coordinateMotion() {
        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appbar_forecast.totalScrollRange.toFloat()
            motion_forecast.progress = seekPosition
        }
        appbar_forecast.addOnOffsetChangedListener(listener)
    }
}
