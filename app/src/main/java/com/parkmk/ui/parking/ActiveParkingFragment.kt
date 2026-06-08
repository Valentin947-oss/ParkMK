package com.parkmk.ui.parking

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.parkmk.R
import com.parkmk.databinding.FragmentActiveParkingBinding
import com.parkmk.model.ParkingSpot
import com.parkmk.model.SampleData
import com.parkmk.model.Vehicle
import com.parkmk.ui.profile.VehiclePickerDialog
import com.parkmk.util.AnalyticsHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ActiveParkingFragment : Fragment(R.layout.fragment_active_parking) {

    private var _b: FragmentActiveParkingBinding? = null
    private val b get() = _b!!

    private val handler = Handler(Looper.getMainLooper())
    private var smsSent = false
    private lateinit var spot: ParkingSpot
    private var activeVehicle: Vehicle? = null

    private val notifPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    private val ticker = object : Runnable {
        override fun run() {
            if (_b == null) return
            val startMs = ParkingTimerService.startMs
            if (!smsSent || !ParkingTimerService.isRunning || startMs == 0L) {
                handler.postDelayed(this, 1000L)
                return
            }
            val elapsed = (System.currentTimeMillis() - startMs) / 1000L
            val h = elapsed / 3600
            val m = (elapsed % 3600) / 60
            val s = elapsed % 60
            b.tvTimer.text = String.format("%02d:%02d:%02d", h, m, s)
            val cost = elapsed / 60.0 * spot.pricePerMinute
            b.tvCost.text = String.format("%.2f ден", cost)
            b.tvTimerStatus.text = requireContext().getString(R.string.sms_active)
            handler.postDelayed(this, 1000L)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentActiveParkingBinding.bind(view)

        val spotId = arguments?.getString("spotId") ?: "s1"
        spot = SampleData.bitolaSpots.find { it.id == spotId }
            ?: SampleData.bitolaSpots.first()

        if (ParkingTimerService.isRunning) {
            smsSent = true
            b.tvSpotName.text = ParkingTimerService.spotName
            b.btnSendSms.text = getString(R.string.sms_sent_success)
            b.btnSendSms.setBackgroundColor(requireContext().getColor(R.color.park_green))
            b.btnSendSms.isEnabled = false
            b.tvTimerStatus.text = getString(R.string.sms_active)
        } else {
            b.tvSpotName.text = spot.name
        }

        b.tvZoneVal.text     = spot.zoneName
        b.tvRateVal.text     = String.format("%.2f %s", spot.pricePerMinute, getString(R.string.den_per_min_unit))
        b.tvStartVal.text    = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        b.tvSmsTo.text       = getString(R.string.sms_to_number)
        b.tvVehicleVal.text  = getString(R.string.loading)
        b.tvTimer.text       = "00:00:00"
        b.tvCost.text        = "0.00 ден"

        if (!ParkingTimerService.isRunning) {
            b.tvTimerStatus.text = getString(R.string.active_timer_hint)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        handler.post(ticker)
        loadVehiclesFromFirestore()

        b.rowVehicle.setOnClickListener { showVehiclePicker() }
        b.btnSendSms.setOnClickListener { sendSms() }
        b.btnStop.setOnClickListener    { stopParking() }
        b.btnBack?.setOnClickListener   { findNavController().popBackStack() }
    }

    private fun loadVehiclesFromFirestore() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Firebase.firestore
            .collection("users").document(uid)
            .collection("vehicles").get()
            .addOnSuccessListener { snapshot ->
                if (_b == null) return@addOnSuccessListener
                val vehicles = snapshot.documents.mapNotNull { doc ->
                    Vehicle(
                        id          = doc.id,
                        plate       = doc.getString("plate") ?: "",
                        plateSms    = doc.getString("plateSms") ?: "",
                        displayName = doc.getString("displayName") ?: "",
                        type        = doc.getString("type") ?: "CAR",
                        colorHex    = doc.getString("colorHex") ?: "#B0B0B0",
                        isActive    = doc.getBoolean("active") ?: false
                    )
                }
                if (vehicles.isNotEmpty()) {
                    val active = vehicles.find { it.isActive } ?: vehicles.first()
                    setVehicle(active)
                } else {
                    b.tvVehicleVal.text = getString(R.string.no_vehicle)
                }
            }
            .addOnFailureListener {
                if (_b == null) return@addOnFailureListener
                b.tvVehicleVal.text = getString(R.string.error_generic)
            }
    }

    private fun setVehicle(vehicle: Vehicle) {
        activeVehicle       = vehicle
        b.tvVehicleVal.text = vehicle.plate
        b.tvSmsZone.text    = spot.zoneId.replace("zone_", "").uppercase()
        b.tvSmsPlate.text   = vehicle.plateSms
    }

    private fun showVehiclePicker() {
        VehiclePickerDialog { selected ->
            setVehicle(selected)
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                    Firebase.firestore
                        .collection("users").document(uid)
                        .collection("vehicles").get()
                        .addOnSuccessListener { snap ->
                            val batch = Firebase.firestore.batch()
                            snap.documents.forEach {
                                batch.update(it.reference, "active", false)
                            }
                            batch.update(
                                Firebase.firestore.collection("users")
                                    .document(uid).collection("vehicles")
                                    .document(selected.id),
                                "active", true
                            )
                            batch.commit()
                        }
                } catch (_: Exception) { }
            }
        }.show(parentFragmentManager, "vehicle_picker")
    }

    private fun sendSms() {
        val plateSms = activeVehicle?.plateSms ?: ""
        if (plateSms.isEmpty()) {
            b.tvTimerStatus.text = getString(R.string.select_vehicle_first)
            return
        }
        val zoneCode = spot.zoneId.replace("zone_", "").uppercase()
        val body     = "$zoneCode $plateSms"
        val uri      = Uri.parse("smsto:144414")
        val intent   = Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra("sms_body", body)
        }
        try {
            startActivity(intent)

            if (!smsSent) {
                val startMs = System.currentTimeMillis()
                val serviceIntent = Intent(requireContext(), ParkingTimerService::class.java).apply {
                    action = ParkingTimerService.ACTION_START
                    putExtra(ParkingTimerService.EXTRA_START_MS, startMs)
                    putExtra(ParkingTimerService.EXTRA_SPOT, spot.name)
                    putExtra(ParkingTimerService.EXTRA_RATE, spot.pricePerMinute)
                    putExtra(ParkingTimerService.EXTRA_SPOT_ID, spot.id)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireContext().startForegroundService(serviceIntent)
                } else {
                    requireContext().startService(serviceIntent)
                }
            }

            smsSent = true
            AnalyticsHelper.logSmsSent(zoneCode, activeVehicle?.plate ?: "")
            AnalyticsHelper.logParkingStarted(spot.name, spot.zoneName, spot.pricePerHour)
            b.btnSendSms.text = getString(R.string.sms_sent_success)
            b.btnSendSms.setBackgroundColor(requireContext().getColor(R.color.park_green))
            b.btnSendSms.isEnabled = false
        } catch (_: Exception) { }
    }

    private fun stopParking() {
        handler.removeCallbacks(ticker)

        requireContext().startService(
            Intent(requireContext(), ParkingTimerService::class.java).apply {
                action = ParkingTimerService.ACTION_STOP
            }
        )

        ParkingNotificationManager.cancelAll()

        val smsUri = Uri.parse("smsto:144414")
        try {
            startActivity(Intent(Intent.ACTION_SENDTO, smsUri).apply {
                putExtra("sms_body", "S")
            })
        } catch (_: Exception) { }

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && smsSent) {
            val startMs = ParkingTimerService.startMs
            val elapsed = (System.currentTimeMillis() - startMs) / 1000L
            val cost    = elapsed / 60.0 * spot.pricePerMinute
            val session = hashMapOf(
                "spotName"    to spot.name,
                "zoneName"    to spot.zoneName,
                "zoneCode"    to spot.zoneId.replace("zone_", "").uppercase(),
                "plate"       to (activeVehicle?.plate ?: ""),
                "startTime"   to Timestamp(startMs / 1000, 0),
                "endTime"     to FieldValue.serverTimestamp(),
                "durationSec" to elapsed,
                "totalCost"   to Math.round(cost * 100.0) / 100.0,
                "smsNumber"   to "144414"
            )
            Firebase.firestore
                .collection("users").document(uid)
                .collection("sessions").add(session)
        }

        AnalyticsHelper.logParkingStopped(
            spot.name,
            (System.currentTimeMillis() - ParkingTimerService.startMs) / 1000L,
            ((System.currentTimeMillis() - ParkingTimerService.startMs) / 1000L) / 60.0 * spot.pricePerMinute
        )

        findNavController().navigate(R.id.action_active_to_receipt)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(ticker)
        super.onDestroyView()
        _b = null
    }
}