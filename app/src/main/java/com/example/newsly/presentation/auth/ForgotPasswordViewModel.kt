package com.example.newsly.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.domain.repository.AuthResult
import com.example.newsly.domain.usecase.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val resetPassword: ResetPasswordUseCase,
) : ViewModel() {
    private val _forgotState = MutableStateFlow(ForgotPasswordState())
    val forgotState: StateFlow<ForgotPasswordState> = _forgotState.asStateFlow()
    fun onEmailChange(email: String) {
        _forgotState.value = _forgotState.value.copy(email = email)
    }

    fun submitResetPassword() {
        val state = _forgotState.value
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _forgotState.value = state.copy(error = "Enter valid email")
            return
        }
        viewModelScope.launch {
            _forgotState.value = state.copy(isLoading = true, error = null)
            when (val res = resetPassword(state.email.trim())) {
                is AuthResult.Success -> {
                    _forgotState.value = _forgotState.value.copy(
                        isLoading = false,
                        snackbarMessage = "Reset email sent. Check your inbox."
                    )
                }

                is AuthResult.Error -> _forgotState.value =
                    _forgotState.value.copy(isLoading = false, error = res.message)

                AuthResult.Loading -> Unit

            }
        }
    }

    fun clearSnackbar() {
        _forgotState.value = _forgotState.value.copy(snackbarMessage = null)
    }
}

data class ForgotPasswordState(
    val email: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null
)

