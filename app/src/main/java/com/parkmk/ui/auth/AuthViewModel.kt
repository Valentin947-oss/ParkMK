package com.parkmk.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.parkmk.data.repository.FirebaseRepository
import com.parkmk.model.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: FirebaseRepository) : ViewModel() {

    private val _state = MutableStateFlow<UiState<FirebaseUser>>(UiState.Empty)
    val state: StateFlow<UiState<FirebaseUser>> = _state.asStateFlow()

    val isLoggedIn: Boolean get() = repo.isLoggedIn

    fun signInAnonymously() = launch {
        repo.signInAnonymously()
    }

    fun signInWithEmail(email: String, password: String) = launch {
        repo.signInWithEmail(email, password)
    }

    fun register(email: String, password: String, name: String, phone: String) = launch {
        repo.registerWithEmail(email, password, name, phone)
    }

    fun signInWithGoogle(account: GoogleSignInAccount) = launch {
        repo.signInWithGoogle(account.idToken!!)
    }

    fun signInWithFacebook(token: com.facebook.AccessToken) = launch {
        repo.signInWithFacebook(token)
    }

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            try {
                repo.sendPasswordResetEmail(email)
                _state.value = UiState.Empty
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Error")
            }
        }
    }

    fun signOut() {
        repo.signOut()
        _state.value = UiState.Empty
    }

    private fun launch(block: suspend () -> FirebaseUser?) =
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val user = block()
                if (user != null) {
                    val token = repo.getFcmToken()
                    repo.updateFcmToken(token)
                    _state.value = UiState.Success(user)
                }
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Error")
            }
        }

    class Factory(private val repo: FirebaseRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repo) as T
        }
    }
}