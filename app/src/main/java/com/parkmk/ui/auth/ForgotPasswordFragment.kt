package com.parkmk.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.parkmk.R
import com.parkmk.databinding.FragmentForgotPasswordBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private var _b: FragmentForgotPasswordBinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentForgotPasswordBinding.bind(view)

        b.btnBack.setOnClickListener { findNavController().navigateUp() }
        b.tvLogin.setOnClickListener { findNavController().navigateUp() }

        b.btnSend.setOnClickListener {
            val email = b.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                b.tilEmail.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                b.tilEmail.error = getString(R.string.error_invalid_email)
                return@setOnClickListener
            }
            b.tilEmail.error = null
            b.btnSend.isEnabled = false
            b.btnSend.text = "Се испраќа..."

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    FirebaseAuth.getInstance()
                        .sendPasswordResetEmail(email)
                        .await()
                    Snackbar.make(
                        b.root,
                        "✓ Линкот е испратен на $email",
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().navigateUp()
                } catch (e: Exception) {
                    Snackbar.make(
                        b.root,
                        "Грешка: ${e.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                    b.btnSend.isEnabled = true
                    b.btnSend.text = getString(R.string.forgot_send_button)
                }
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}