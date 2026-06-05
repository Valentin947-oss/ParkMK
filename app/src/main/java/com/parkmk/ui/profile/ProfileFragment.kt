package com.parkmk.ui.profile

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.parkmk.ui.map.MainActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.parkmk.R
import com.parkmk.data.repository.FirebaseRepository
import com.parkmk.databinding.FragmentProfileBinding
import com.parkmk.model.Vehicle
import com.parkmk.ui.auth.AuthActivity
import com.parkmk.util.AnalyticsHelper
import java.util.Locale

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _b: FragmentProfileBinding? = null
    private val b get() = _b!!
    private val repo = FirebaseRepository()
    private lateinit var adapter: VehicleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentProfileBinding.bind(view)

        val user = repo.currentUser
        val name = user?.displayName ?: "Корисник"
        val initials = name.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.toString() }
            .joinToString("")
        b.tvAvatarInitials.text = initials.ifEmpty { "К" }
        b.tvUserName.text       = name
        b.tvUserEmail.text      = user?.email ?: ""
        b.tvProfileName.text    = user?.displayName ?: "—"
        b.tvProfileEmail.text   = user?.email ?: "—"

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    b.tvProfilePhone.text = doc.getString("phone") ?: "—"
                }
        }

        adapter = VehicleAdapter(
            vehicles    = emptyList(),
            onSetActive = { vehicle -> setActiveVehicle(vehicle) },
            onDelete    = { vehicle -> confirmDelete(vehicle) }
        )
        b.rvVehicles.layoutManager = LinearLayoutManager(requireContext())
        b.rvVehicles.adapter = adapter

        loadVehicles()

        b.btnAddVehicle.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_addVehicle)
        }

        b.btnLanguage.setOnClickListener {
            showLanguagePicker()
        }

        b.btnLogout.setOnClickListener {
            repo.signOut()
            startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun showLanguagePicker() {
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val currentLang = prefs.getString("language", "mk") ?: "mk"
        val options = arrayOf("🇲🇰  Македонски (MKD)", "🇬🇧  English (ENG)")
        val selected = if (currentLang == "mk") 0 else 1

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Јазик / Language")
            .setSingleChoiceItems(options, selected) { dialog, index ->
                val lang = if (index == 0) "mk" else "en"
                prefs.edit().putString("language", lang).apply()
                dialog.dismiss()
                AnalyticsHelper.logLanguageChanged(lang)

                // Директно рестартирај го процесот
                val intent = Intent(requireContext(), com.parkmk.ui.auth.SplashActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                requireContext().startActivity(intent)
                android.os.Process.killProcess(android.os.Process.myPid())
            }
            .setNegativeButton("Откажи", null)
            .show()
    }

    private fun updateTexts(lang: String) {
        // Рачно ажурирај ги текстовите во ProfileFragment
        if (lang == "en") {
            b.btnLogout.text = "Sign Out"
            b.btnAddVehicle.text = "Add Vehicle"
            b.btnLanguage.text = "Language"
            // додај ги сите текстови што ги имаш
        } else {
            b.btnLogout.text = "Одјави се"
            b.btnAddVehicle.text = "Додај возило"
            b.btnLanguage.text = "Јазик / Language"
        }
    }

    private fun loadVehicles() {
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
                adapter.update(vehicles)
            }
            .addOnFailureListener { e ->
                android.util.Log.e("Profile", "Error: ${e.message}")
            }
    }

    private fun setActiveVehicle(vehicle: Vehicle) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val batch = Firebase.firestore.batch()
        Firebase.firestore
            .collection("users").document(uid)
            .collection("vehicles").get()
            .addOnSuccessListener { snap ->
                snap.documents.forEach { batch.update(it.reference, "active", false) }
                batch.update(
                    Firebase.firestore.collection("users")
                        .document(uid).collection("vehicles")
                        .document(vehicle.id), "active", true
                )
                batch.commit().addOnSuccessListener { loadVehicles() }
            }
    }

    private fun confirmDelete(vehicle: Vehicle) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Избриши возило")
            .setMessage("Дали сакаш да го избришеш ${vehicle.plate}?")
            .setPositiveButton("Избриши") { _, _ ->
                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setPositiveButton
                Firebase.firestore
                    .collection("users").document(uid)
                    .collection("vehicles").document(vehicle.id)
                    .delete()
                    .addOnSuccessListener { loadVehicles() }
            }
            .setNegativeButton("Откажи", null)
            .show()
    }

    override fun onResume() { super.onResume(); loadVehicles() }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}