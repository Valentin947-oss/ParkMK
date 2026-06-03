package com.parkmk.ui.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.parkmk.model.Vehicle

class VehiclePickerDialog(
    private val onVehicleSelected: (Vehicle) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid == null) {
            return AlertDialog.Builder(requireContext())
                .setTitle("Грешка")
                .setMessage("Не сте логирани")
                .setPositiveButton("OK", null)
                .create()
        }

        // Директно читај од Firestore без Repository
        Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("vehicles")
            .get()
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

                if (vehicles.isEmpty()) {
                    Handler(Looper.getMainLooper()).post {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Нема возила")
                            .setMessage("Додај возило во Профил → Додај ново возило")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                    return@addOnSuccessListener
                }

                val labels = vehicles.map { v ->
                    if (v.isActive) "✓  ${v.plate} — ${v.displayName}"
                    else "    ${v.plate} — ${v.displayName}"
                }.toTypedArray()

                Handler(Looper.getMainLooper()).post {
                    if (isAdded && context != null) {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Избери возило")
                            .setItems(labels) { _, index ->
                                onVehicleSelected(vehicles[index])
                            }
                            .setNegativeButton("Откажи", null)
                            .show()
                    }
                }
            }
            .addOnFailureListener { e ->
                Handler(Looper.getMainLooper()).post {
                    if (isAdded && context != null) {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Грешка")
                            .setMessage(e.message)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
            }

        // Враќа празен dialog додека се вчитува
        return AlertDialog.Builder(requireContext())
            .setTitle("Избери возило")
            .setMessage("Се вчитуваат возилата...")
            .create()
    }
}