package com.kovalenko.weatherforecast.features.picker.ui

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.kovalenko.weatherforecast.R
import com.kovalenko.weatherforecast.features.picker.viewmodel.PlacePickerViewModel
import kotlinx.android.synthetic.main.fragment_place_picker.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlacePickerFragment : Fragment() {
    private lateinit var map: GoogleMap
    private val pickerViewModel by viewModel<PlacePickerViewModel>()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.setOnMapLongClickListener(::onMapLongPress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val elevationScale = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.transition_default_duration).toLong()
        }
        enterTransition = MaterialFadeThrough()
        exitTransition = elevationScale
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fab_help.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                    .setTitle(getString(R.string.map_help))
                    .setMessage(getString(R.string.map_instruction))
                    .setPositiveButton(getString(R.string.dismiss)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
        }
    }

    private fun onMapLongPress(latLng: LatLng) {
        view?.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        val dialog = MaterialAlertDialogBuilder(requireActivity())
                .setTitle(getString(R.string.pick_location))
                .setMessage(getString(R.string.picker_body))
                .setView(R.layout.dialog_picker)
                .setPositiveButton(getString(R.string.save)) { _, _ ->
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val inputLayout = (dialog as AlertDialog).findViewById<TextInputLayout>(R.id.input_layout_city)
            val text = inputLayout?.editText?.text.toString()
            if (text.isEmpty()) {
                inputLayout?.error = getString(R.string.error_name_empty)
            } else {
                pickerViewModel.saveLocation(text, latLng)
                dialog.dismiss()
            }
        }
    }
}
