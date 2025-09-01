package com.example.newsapp.presentation.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(firebaseAuth: FirebaseAuth) : ViewModel(){
    private val _splashState = MutableStateFlow<SplashState>(SplashState.Loading)
    val splashState = _splashState.asStateFlow()
    init {
        viewModelScope.launch {
            delay(2000)
            if (firebaseAuth.currentUser != null) {
                _splashState.value = SplashState.NavigateToHome
            } else {
                _splashState.value = SplashState.NavigateToLogin
            }
        }
    }
}

sealed class SplashState {
    object Loading : SplashState()
    object NavigateToHome : SplashState()
    object NavigateToLogin : SplashState()
}