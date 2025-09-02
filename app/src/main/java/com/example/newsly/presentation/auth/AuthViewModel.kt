package com.example.newsly.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.domain.repository.AuthResult
import com.example.newsly.domain.usecase.ResetPasswordUseCase
import com.example.newsly.domain.usecase.SignInUseCase
import com.example.newsly.domain.usecase.SignOutUseCase
import com.example.newsly.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val signUp: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _login = MutableStateFlow(AuthFormState())
    val login: StateFlow<AuthFormState> = _login.asStateFlow()

    private val _signup = MutableStateFlow(AuthFormState())
    val signup: StateFlow<AuthFormState> = _signup.asStateFlow()

    fun onLoginEmailChanged(email: String) {
        _login.value = _login.value.copy(email = email, error = null)
    }

    fun onLoginPasswordChanged(password: String) {
        _login.value = _login.value.copy(password = password, error = null)
    }

    fun onSignupNameChanged(v: String) {
        _signup.value = _signup.value.copy(name = v, error = null)
    }

    fun onSignupEmailChanged(v: String) {
        _signup.value = _signup.value.copy(email = v, error = null)
    }

    fun onSignupPasswordChanged(v: String) {
        _signup.value = _signup.value.copy(password = v, error = null)
    }

    fun submitLogin(onSuccess: () -> Unit) {
        val state = _login.value
        if (!state.isValidForAuth()) {
            _login.value = state.copy(error = "Enter valid email and 6+ char password")
            return
        }
        viewModelScope.launch {
            _login.value = state.copy(isLoading = true, error = null)
            when (val res = signIn(state.email.trim(), state.password)) {
                is AuthResult.Success -> onSuccess()
                is AuthResult.Error -> _login.value =
                    _login.value.copy(isLoading = false, error = res.message)

                AuthResult.Loading -> Unit
            }
        }
    }

    fun submitSignup(onSuccess: () -> Unit) {
        val state = _signup.value
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _signup.value = state.copy(error = "Enter valid email and 6+ char password")
            return
        }
        viewModelScope.launch {
            _signup.value = state.copy(isLoading = true, error = null)
            when (val res =
                signUp(state.email.trim(), state.password, state.name.trim().ifBlank { null })) {
                is AuthResult.Success -> onSuccess()
                is AuthResult.Error -> _signup.value =
                    _signup.value.copy(isLoading = false, error = res.message)

                AuthResult.Loading -> Unit
            }
        }
    }

    fun signOut(onSignedOut: () -> Unit) {
        signOutUseCase()
        onSignedOut()
    }
}

private fun AuthFormState.isValidForAuth(): Boolean =
    android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6