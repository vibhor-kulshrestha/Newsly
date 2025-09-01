package com.example.newsly.domain.repository

import com.example.newsly.domain.User
import kotlinx.coroutines.flow.Flow

sealed interface AuthResult {
    data class Success(val user: User) : AuthResult
    data class Error(val message: String) : AuthResult
    data object Loading : AuthResult
}

interface AuthRepository {
    val authState: Flow<User?>
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String, displayName: String?): AuthResult
    fun signOut()
}