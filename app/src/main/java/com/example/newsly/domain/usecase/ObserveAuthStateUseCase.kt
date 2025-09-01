package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.AuthRepository

class ObserveAuthStateUseCase(private val repo: AuthRepository) {
    operator fun invoke() = repo.authState
}