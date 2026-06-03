package com.parkmk.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _b: FragmentProfileBinding? = null
    private val b get() = _b!!
    private val repo = FirebaseRepository()
    private lateinit var adapter: VehicleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentProfileBinding.bind(view)

        // Корисник инфо
        val user = repo.currentUser
        val name = user?.displayName ?: "Корисник"
        val initials = name.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.toString() }
            .joinToString("")
        b.tvAvatarInitials.text = initials.ifEmpty { "К" }
        b.tvUserName.text       = name
        b.tvUserEmail.text      = user?.email ?: ""

        // RecyclerView setup
        adapter = VehicleAdapter(
            vehicles    = emptyList(),
            onSetActive = { vehicle -> setActiveVehicle(vehicle) },
            onDelete    = { vehicle -> confirmDelete(vehicle) }
        )
        b.rvVehicles.layoutManager = LinearLayoutManager(requireContext())
        b.rvVehicles.adapter = adapter

        // Вчитај возила
        loadVehicles()

        // Додај возило
        b.btnAddVehicle.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_addVehicle)
        }

        // Sign out
        b.btnLogout.setOnClickListener {
            repo.signOut()
            startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun loadVehicles() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

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
                adapter.update(vehicles)
            }
            .addOnFailureListener { e ->
                android.util.Log.e("Profile", "Error loading vehicles: ${e.message}")
            }
    }

    private fun setActiveVehicle(vehicle: Vehicle) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val batch = Firebase.firestore.batch()

        Firebase.firestore
            .collection("users").document(uid)
            .collection("vehicles").get()
            .addOnSuccessListener { snap ->
                snap.documents.forEach {
                    batch.update(it.reference, "active", false)
                }
                batch.update(
                    Firebase.firestore.collection("users")
                        .document(uid).collection("vehicles")
                        .document(vehicle.id),
                    "active", true
                )
                batch.commit().addOnSuccessListener {
                    loadVehicles()
                }
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

    override fun onResume() {
        super.onResume()
        loadVehicles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}