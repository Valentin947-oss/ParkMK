package com.parkmk.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.parkmk.R
import com.parkmk.databinding.FragmentMapBinding
import com.parkmk.model.ParkingSpot
import com.parkmk.model.SampleData
import com.parkmk.util.AnalyticsHelper
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment(R.layout.fragment_map) {

    private var _b: FragmentMapBinding? = null
    private val b get() = _b!!
    private var selectedSpot: ParkingSpot? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var locationOverlay: MyLocationNewOverlay

    private val locationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) enableMyLocation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentMapBinding.bind(view)

        Configuration.getInstance().load(
            requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )
        Configuration.getInstance().userAgentValue = "ParkMK/1.0"

        b.mapView.setTileSource(TileSourceFactory.MAPNIK)
        b.mapView.setMultiTouchControls(true)

        val mapController = b.mapView.controller
        mapController.setZoom(15.5)
        mapController.setCenter(GeoPoint(41.0280, 21.3350))

        bottomSheetBehavior = BottomSheetBehavior.from(b.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight = 80

        addParkingMarkers()
        setupLocation()

        b.btnParkHere?.setOnClickListener {
            selectedSpot?.let { spot ->
                val bundle = Bundle().apply {
                    putString("spotId", spot.id)
                }
                findNavController().navigate(R.id.action_map_to_activePark, bundle)
            }
        }
        AnalyticsHelper.logScreen("map_screen")
    }

    private fun setupLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation()
        } else {
            locationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun enableMyLocation() {
        locationOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(requireContext()),
            b.mapView
        )
        locationOverlay.enableMyLocation()

        // Постави person иконка
        val personDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_person)
        if (personDrawable != null) {
            personDrawable.setTint(resources.getColor(R.color.park_red, null))
            val size = 64
            val bitmap = android.graphics.Bitmap.createBitmap(size, size, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            personDrawable.setBounds(0, 0, size, size)
            personDrawable.draw(canvas)
            locationOverlay.setPersonIcon(bitmap)
            locationOverlay.setDirectionIcon(bitmap)
        }

        locationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                b.mapView.controller.setZoom(17.0)
                b.mapView.controller.animateTo(locationOverlay.myLocation)
            }
        }

        b.mapView.overlays.add(0, locationOverlay)
        b.mapView.invalidate()
    }

    private fun addParkingMarkers() {
        SampleData.bitolaSpots.forEach { spot ->
            val marker = Marker(b.mapView)
            marker.position = GeoPoint(spot.latitude, spot.longitude)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = spot.name
            marker.snippet = "${spot.zoneName} · ${spot.pricePerHour} ден/час"
            marker.setOnMarkerClickListener { _, _ ->
                selectedSpot = spot
                showSpotDetail(spot)
                true
            }
            b.mapView.overlays.add(marker)
        }
        b.mapView.invalidate()
    }

    private fun showSpotDetail(spot: ParkingSpot) {
        b.layoutEmpty?.isVisible  = false
        b.layoutDetail?.isVisible = true
        b.tvSpotName?.text  = spot.name
        b.tvSpotAddr?.text  = spot.address
        b.tvZoneBadge?.text = spot.zoneName
        b.tvPrice?.text     = "${spot.pricePerHour} ден/час"
        b.tvPerMin?.text    = "%.2f ден/мин".format(spot.pricePerMinute)
        b.tvHours?.text     = "${spot.openFrom}–${spot.openUntil}"
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        b.mapView.controller.animateTo(GeoPoint(spot.latitude, spot.longitude))
        b.tvAvailableSpots?.text = "${spot.totalSpots} ${getString(R.string.available_spots)}"
    }

    override fun onResume() {
        super.onResume()
        b.mapView.onResume()
        if (::locationOverlay.isInitialized) locationOverlay.enableMyLocation()
    }

    override fun onPause() {
        super.onPause()
        b.mapView.onPause()
        if (::locationOverlay.isInitialized) locationOverlay.disableMyLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}