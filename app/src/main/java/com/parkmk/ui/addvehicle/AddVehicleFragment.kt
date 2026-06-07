package com.parkmk.ui.addvehicle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.parkmk.R
import com.parkmk.data.repository.FirebaseRepository
import com.parkmk.databinding.FragmentAddVehicleBinding
import com.parkmk.util.AnalyticsHelper
import kotlinx.coroutines.launch

class AddVehicleFragment : Fragment(R.layout.fragment_add_vehicle) {

    private var _b: FragmentAddVehicleBinding? = null
    private val b get() = _b!!
    private val repo = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentAddVehicleBinding.bind(view)

        val user = FirebaseAuth.getInstance().currentUser
        android.util.Log.d("AddVehicle", "Current user UID: ${user?.uid}")

        b.etPlate.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                val clean = s.toString().uppercase()
                    .replace("-", "")
                    .replace(" ", "")
                b.tvPlatePreview.text = clean.ifEmpty { "ВТ 0000 АА" }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        b.btnSave.setOnClickListener {
            val plate = b.etPlate.text.toString().trim().uppercase()
            val name  = b.etNick.text.toString().trim()

            if (plate.isEmpty()) {
                b.tilPlate.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            if (plate.length < 4) {
                b.tilPlate.error = "Внеси валидна таблица"
                return@setOnClickListener
            }
            b.tilPlate.error = null

            viewLifecycleOwner.lifecycleScope.launch {
                b.btnSave.isEnabled = false
                b.btnSave.text = "Се зачувува..."
                try {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser == null) {
                        Snackbar.make(b.root, "Не сте логирани!", Snackbar.LENGTH_LONG).show()
                        b.btnSave.isEnabled = true
                        b.btnSave.text = getString(R.string.add_vehicle_save)
                        return@launch
                    }

                    val displayName = name.ifEmpty { plate }
                    repo.addVehicle(plate, displayName, "CAR", "#B0B0B0")
                    AnalyticsHelper.logVehicleAdded("CAR")

                    Snackbar.make(b.root, "✓ Возилото $plate е зачувано!", Snackbar.LENGTH_SHORT).show()

                    // Оди во MainActivity → Profile
                    goToMain()

                } catch (e: Exception) {
                    Snackbar.make(b.root, "Грешка: ${e.message}", Snackbar.LENGTH_LONG).show()
                    b.btnSave.isEnabled = true
                    b.btnSave.text = getString(R.string.add_vehicle_save)
                }
            }
        }

        b.btnSkip?.setOnClickListener { goToMain() }
        b.btnBack?.setOnClickListener { findNavController().navigateUp() }
    }

    private fun goToMain() {
        val intent = Intent(requireContext(), com.parkmk.ui.map.MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}