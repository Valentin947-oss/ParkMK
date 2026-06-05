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
    private var startMs = 0L
    private var smsSent = false
    private lateinit var spot: ParkingSpot
    private var activeVehicle: Vehicle? = null

    private val notifPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        android.util.Log.d("Notif", "Permission granted: $granted")
    }

    private val ticker = object : Runnable {
        override fun run() {
            val elapsed = (System.currentTimeMillis() - startMs) / 1000L
            val h = elapsed / 3600
            val m = (elapsed % 3600) / 60
            val s = elapsed % 60
            b.tvTimer.text = String.format("%02d:%02d:%02d", h, m, s)
            val cost = elapsed / 60.0 * spot.pricePerMinute
            b.tvCost.text = String.format("%.2f ден", cost)
            if (smsSent) b.tvTimerStatus.text = "✓ SMS активна · тече наплата"
            handler.postDelayed(this, 1000L)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentActiveParkingBinding.bind(view)

        val spotId = arguments?.getString("spotId") ?: "s1"
        spot = SampleData.bitolaSpots.find { it.id == spotId }
            ?: SampleData.bitolaSpots.first()

        b.tvSpotName.text    = spot.name
        b.tvZoneVal.text     = spot.zoneName
        b.tvRateVal.text     = "%.2f ден/мин".format(spot.pricePerMinute)
        b.tvStartVal.text    = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        b.tvSmsTo.text       = "На број: 144414"
        b.tvVehicleVal.text  = "Се вчитува..."
        b.tvTimer.text       = "00:00:00"
        b.tvCost.text        = "0.00 ден"
        b.tvTimerStatus.text = "Притисни 'Send SMS' за да почне наплатата"

        // Барај дозвола за нотификации на Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

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
                    b.tvVehicleVal.text = "Немате возило → кликни"
                }
            }
            .addOnFailureListener {
                b.tvVehicleVal.text = "Грешка при вчитување"
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
                } catch (e: Exception) { }
            }
        }.show(parentFragmentManager, "vehicle_picker")
    }

    private fun sendSms() {
        val plateSms = activeVehicle?.plateSms ?: ""
        if (plateSms.isEmpty()) {
            b.tvTimerStatus.text = "⚠ Прво избери возило!"
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
                startMs = System.currentTimeMillis()
                handler.removeCallbacks(ticker)
                handler.post(ticker)
                ParkingNotificationManager.scheduleTestNotification(
                    requireContext(), spot.name
                )
            }
            smsSent = true
            AnalyticsHelper.logSmsSent(
                spot.zoneId.replace("zone_", "").uppercase(),
                activeVehicle?.plate ?: ""
            )
            AnalyticsHelper.logParkingStarted(spot.name, spot.zoneName, spot.pricePerHour)
            b.btnSendSms.text = "✓ SMS испратена на 144414 ✓"
            b.btnSendSms.setBackgroundColor(requireContext().getColor(R.color.park_green))
            b.btnSendSms.isEnabled = false
        } catch (_: Exception) { }
    }

    private fun stopParking() {
        handler.removeCallbacks(ticker)
        ParkingNotificationManager.cancelAll()

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && smsSent) {
            val elapsed = (System.currentTimeMillis() - startMs) / 1000L
            val cost = elapsed / 60.0 * spot.pricePerMinute
            val session = hashMapOf(
                "spotName"    to spot.name,
                "zoneName"    to spot.zoneName,
                "zoneCode"    to spot.zoneId.replace("zone_", "").uppercase(),
                "plate"       to (activeVehicle?.plate ?: ""),
                "startTime"   to Timestamp(startMs / 1000, 0),
                "endTime"     to FieldValue.serverTimestamp(),
                "durationSec" to elapsed,
                "totalCost" to Math.round(cost * 100.0) / 100.0,
                "smsNumber"   to "144414"
            )
            Firebase.firestore
                .collection("users").document(uid)
                .collection("sessions").add(session)
        }

        findNavController().navigate(R.id.action_active_to_receipt)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(ticker)
        super.onDestroyView()
        _b = null
    }
}