package com.parkmk.ui.map

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.parkmk.R
import com.parkmk.databinding.FragmentMapBinding
import com.parkmk.model.ParkingSpot
import com.parkmk.model.SampleData
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment(R.layout.fragment_map) {

    private var _b: FragmentMapBinding? = null
    private val b get() = _b!!
    private var selectedSpot: ParkingSpot? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

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

        b.btnParkHere?.setOnClickListener {
            selectedSpot?.let { spot ->
                // Прати го spotId до ActiveParkingFragment
                val bundle = Bundle().apply {
                    putString("spotId", spot.id)
                }
                findNavController().navigate(R.id.action_map_to_activePark, bundle)
            }
        }
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
    }

    override fun onResume() { super.onResume(); b.mapView.onResume() }
    override fun onPause()  { super.onPause();  b.mapView.onPause()  }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}