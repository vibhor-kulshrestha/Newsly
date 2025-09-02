package com.example.newsly.data.repository

import com.example.newsly.domain.User
import com.example.newsly.domain.repository.AuthRepository
import com.example.newsly.domain.repository.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.contracts.contract
import kotlin.coroutines.resume

class FirebaseAuthRepository(private val auth: FirebaseAuth) : AuthRepository {
    override val authState: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val fbUSer = firebaseAuth.currentUser
            trySend(
                fbUSer?.let {
                    User(it.uid, it.email, it.displayName)
                }
            )
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        return suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!cont.isActive) return@addOnCompleteListener
                    if (task.isSuccessful) {
                        val u = auth.currentUser!!
                        cont.resume(AuthResult.Success(User(u.uid, u.email, u.displayName)))
                    } else {
                        val msg = task.exception?.localizedMessage ?: "Login failed"
                        cont.resume(AuthResult.Error(msg))
                    }
                }
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        displayName: String?
    ): AuthResult {
        return suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!cont.isActive) return@addOnCompleteListener
                    if (task.isSuccessful) {
                        val u = auth.currentUser!!
                        if (!displayName.isNullOrBlank()) {
                            val req = UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName).build()
                            u.updateProfile(req)
                            cont.resume(AuthResult.Success(User(u.uid, u.email, u.displayName)))
                        }
                    } else {
                        val msg = task.exception?.localizedMessage ?: "Signup failed"
                        cont.resume(AuthResult.Error(msg))
                    }
                }
        }
    }

    override suspend fun resetPassword(email: String): AuthResult {
        return suspendCancellableCoroutine { cont ->
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if(!cont.isActive) return@addOnCompleteListener
                    if(task.isSuccessful) {
                        cont.resume(AuthResult.Success(null))
                    } else {
                        val msg = task.exception?.localizedMessage ?: "Failed To reset email"
                        cont.resume(AuthResult.Error(msg))
                    }
                }
        }
    }
    override fun signOut() {
        auth.signOut()
    }
}