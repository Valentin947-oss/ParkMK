package com.parkmk.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.parkmk.R
import com.parkmk.data.repository.FirebaseRepository
import com.parkmk.databinding.FragmentForgotPasswordBinding
import com.parkmk.model.UiState
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private var _b: FragmentForgotPasswordBinding? = null
    private val b get() = _b!!
    private val vm: AuthViewModel by viewModels {
        AuthViewModel.Factory(FirebaseRepository())
    }

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
            vm.sendPasswordReset(email)
            Snackbar.make(b.root, getString(R.string.forgot_email_sent, email), Snackbar.LENGTH_LONG).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
