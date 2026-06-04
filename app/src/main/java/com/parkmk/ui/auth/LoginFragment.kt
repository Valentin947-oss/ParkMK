package com.parkmk.ui.auth

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.parkmk.R
import com.parkmk.data.repository.FirebaseRepository
import com.parkmk.databinding.FragmentLoginBinding
import com.parkmk.model.UiState
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _b: FragmentLoginBinding? = null
    private val b get() = _b!!
    private val vm: AuthViewModel by viewModels {
        AuthViewModel.Factory(FirebaseRepository())
    }

    private lateinit var googleClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    private val googleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val account = GoogleSignIn
                    .getSignedInAccountFromIntent(result.data)
                    .getResult(ApiException::class.java)
                vm.signInWithGoogle(account)
            } catch (e: ApiException) {
                showError("Google грешка: ${e.message}")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentLoginBinding.bind(view)

        // Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Facebook
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    vm.signInWithFacebook(result.accessToken)
                }

                override fun onCancel() {}
                override fun onError(error: FacebookException) {
                    showError("Facebook грешка: ${error.message}")
                }
            })

        // Clicks
        b.btnLogin.setOnClickListener { doLogin() }
        b.btnGoogle.setOnClickListener {
            googleLauncher.launch(googleClient.signInIntent)
        }
        b.btnFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this, callbackManager, listOf("email", "public_profile")
            )
        }
        b.btnAnonymous.setOnClickListener {
            vm.signInAnonymously()
        }
        b.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_forgot)
        }
        b.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        // Observe auth state
        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        (requireActivity() as AuthActivity).goToMain()
                    }

                    is UiState.Error -> {
                        Snackbar.make(b.root, state.message, Snackbar.LENGTH_LONG).show()
                    }

                    is UiState.Loading -> {}
                    else -> {}
                }
            }
        }
    }

    private fun doLogin() {
        val email = b.etEmail.text.toString().trim()
        val pass  = b.etPassword.text.toString()

        b.tilEmail.error    = null
        b.tilPassword.error = null

        var valid = true
        if (email.isEmpty()) {
            b.tilEmail.error = getString(R.string.error_field_required)
            valid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            b.tilEmail.error = getString(R.string.error_invalid_email)
            valid = false
        }
        if (pass.isEmpty()) {
            b.tilPassword.error = getString(R.string.error_field_required)
            valid = false
        } else if (pass.length < 6) {
            b.tilPassword.error = "Минимум 6 карактери"
            valid = false
        }

        if (valid) vm.signInWithEmail(email, pass)
    }

    private fun showSuccess() {
        Snackbar.make(b.root, "Успешно логирање! ✓", Snackbar.LENGTH_SHORT).show()
    }

    private fun showError(msg: String) {
        val friendlyMsg = when {
            msg.contains("no user record")       -> "Корисникот не постои"
            msg.contains("password is invalid")  -> "Погрешна лозинка"
            msg.contains("badly formatted")      -> "Невалидна е-пошта"
            msg.contains("network error")        -> "Нема интернет врска"
            msg.contains("blocked")              -> "Премногу обиди, пробај подоцна"
            else -> msg
        }
        Snackbar.make(b.root, friendlyMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}