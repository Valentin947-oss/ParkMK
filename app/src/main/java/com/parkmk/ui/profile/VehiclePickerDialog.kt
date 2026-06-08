package com.parkmk.ui.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
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

        val loadingDialog = AlertDialog.Builder(requireContext())
            .setTitle("Избери возило")
            .setMessage("Се вчитуваат возилата...")
            .setCancelable(true)
            .create()

        Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("vehicles")
            .get()
            .addOnSuccessListener { snapshot ->
                if (!isAdded || context == null) return@addOnSuccessListener

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

                // Го затвораме loading-от
                loadingDialog.dismiss()

                if (vehicles.isEmpty()) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Нема возила")
                        .setMessage("Додај возило во Профил → Додај ново возило")
                        .setPositiveButton("OK", null)
                        .show()
                    return@addOnSuccessListener
                }

                val labels = vehicles.map { v ->
                    if (v.isActive) "✓  ${v.plate} — ${v.displayName}"
                    else "    ${v.plate} — ${v.displayName}"
                }.toTypedArray()

                AlertDialog.Builder(requireContext())
                    .setTitle("Избери возило")
                    .setItems(labels) { _, index ->
                        onVehicleSelected(vehicles[index])
                    }
                    .setNegativeButton("Откажи", null)
                    .show()
            }
            .addOnFailureListener { e ->
                if (!isAdded || context == null) return@addOnFailureListener
                loadingDialog.dismiss()
                AlertDialog.Builder(requireContext())
                    .setTitle("Грешка")
                    .setMessage(e.message)
                    .setPositiveButton("OK", null)
                    .show()
            }

        return loadingDialog
    }
}