package com.example.newsly.presentation.auth

data class AuthFormState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)