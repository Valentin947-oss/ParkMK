package com.parkmk.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.parkmk.R
import com.parkmk.data.repository.FirebaseRepository
import com.parkmk.databinding.FragmentRegisterBinding
import com.parkmk.model.UiState
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _b: FragmentRegisterBinding? = null
    private val b get() = _b!!
    private val vm: AuthViewModel by viewModels {
        AuthViewModel.Factory(FirebaseRepository())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentRegisterBinding.bind(view)

        b.btnBack.setOnClickListener { findNavController().navigateUp() }
        b.tvLogin.setOnClickListener { findNavController().navigateUp() }

        b.btnRegister.setOnClickListener { doRegister() }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collect { state ->
                b.progressBar.isVisible = state is UiState.Loading
                b.btnRegister.isEnabled = state !is UiState.Loading
                when (state) {
                    is UiState.Success -> {
                        Snackbar.make(b.root, "Профилот е создаден! ✓", Snackbar.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_register_to_addVehicle)
                    }
                    is UiState.Error -> {
                        Snackbar.make(b.root, state.message, Snackbar.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun doRegister() {
        val name  = b.etName.text.toString().trim()
        val email = b.etEmail.text.toString().trim()
        val phone = b.etPhone.text.toString().trim()
        val pass  = b.etPassword.text.toString()
        val pass2 = b.etConfirmPassword.text.toString()

        // Reset errors
        listOf(b.tilName, b.tilEmail, b.tilPhone, b.tilPassword, b.tilConfirmPassword)
            .forEach { it.error = null }

        var valid = true
        if (name.isEmpty()) {
            b.tilName.error = getString(R.string.error_field_required)
            valid = false
        }
        if (email.isEmpty()) {
            b.tilEmail.error = getString(R.string.error_field_required)
            valid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            b.tilEmail.error = getString(R.string.error_invalid_email)
            valid = false
        }
        if (phone.isEmpty()) {
            b.tilPhone.error = getString(R.string.error_field_required)
            valid = false
        }
        if (pass.isEmpty()) {
            b.tilPassword.error = getString(R.string.error_field_required)
            valid = false
        } else if (pass.length < 8) {
            b.tilPassword.error = getString(R.string.error_password_too_short)
            valid = false
        }
        if (pass != pass2) {
            b.tilConfirmPassword.error = getString(R.string.error_passwords_dont_match)
            valid = false
        }

        if (valid) vm.register(email, pass, name, phone)
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}