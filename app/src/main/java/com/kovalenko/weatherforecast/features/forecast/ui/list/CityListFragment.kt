package com.kovalenko.weatherforecast.features.forecast.ui.list

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.kovalenko.weatherforecast.R
import com.kovalenko.weatherforecast.databinding.CityListFragmentBinding
import com.kovalenko.weatherforecast.databinding.ItemCityBinding
import com.kovalenko.weatherforecast.features.forecast.model.domain.City
import com.kovalenko.weatherforecast.features.forecast.viewmodel.CityListViewModel
import com.kovalenko.weatherforecast.view.MarginItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityListFragment : Fragment() {
    private lateinit var viewDataBinding: CityListFragmentBinding
    private var citiesAdapter: CityListAdapter? = null
    private val cityListViewModel by viewModel<CityListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reenter = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.transition_default_duration).toLong()
        }
        enterTransition = MaterialFadeThrough()
        reenterTransition = reenter
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = CityListFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CityListFragment.viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        coordinateMotion()
        setupRecycler()
        setupSubscriptions()
    }

    override fun onResume() {
        super.onResume()
        exitTransition = null
    }

    private fun setupSubscriptions() {
        cityListViewModel.cities.observe(this.viewLifecycleOwner, Observer(::onCityList))
    }

    private fun onCityList(list: List<City>) {
        citiesAdapter?.submitList(list)
    }

    private fun onCityClick(binding: ItemCityBinding, city: City) {
        val directions = CityListFragmentDirections.showForecast(city)
        val extras = FragmentNavigatorExtras(binding.cityRoot to "container_detail")
        val exit = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.transition_default_duration).toLong()
        }
        exitTransition = exit
        findNavController().navigate(directions, extras)
    }

    private fun setupRecycler() {
        citiesAdapter = CityListAdapter(CityClick(::onCityClick))
        viewDataBinding.recyclerCities.apply {
            layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = citiesAdapter
            addItemDecoration(
                    MarginItemDecorator(
                            resources.getDimension(R.dimen.margin_default).toInt()
                    )
            )
        }
    }

    private fun coordinateMotion() {
        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / viewDataBinding.appbarLayout.totalScrollRange.toFloat()
            viewDataBinding.motionList.progress = seekPosition
        }
        viewDataBinding.appbarLayout.addOnOffsetChangedListener(listener)
    }
}

class CityClick(val block: (ItemCityBinding, City) -> Unit) {
    fun onClick(cityBinding: ItemCityBinding, city: City) = block(cityBinding, city)
}
