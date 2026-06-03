package com.parkmk.ui.auth

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
import com.parkmk.databinding.FragmentWelcomeBinding
import com.parkmk.model.UiState
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private var _b: FragmentWelcomeBinding? = null
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
                Snackbar.make(b.root, "Google грешка: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentWelcomeBinding.bind(view)

        // Google setup
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Facebook setup
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    vm.signInWithFacebook(result.accessToken)
                }
                override fun onCancel() {}
                override fun onError(error: FacebookException) {
                    Snackbar.make(b.root, "Facebook грешка: ${error.message}", Snackbar.LENGTH_LONG).show()
                }
            })

        // Clicks
        b.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_to_register)
        }
        b.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_to_login)
        }
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

        // Observe
        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collect { state ->
                when (state) {
                    is UiState.Success -> (requireActivity() as AuthActivity).goToMain()
                    is UiState.Error -> Snackbar.make(b.root, state.message, Snackbar.LENGTH_LONG).show()
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}