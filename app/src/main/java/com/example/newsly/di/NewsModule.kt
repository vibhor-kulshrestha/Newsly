package com.example.newsly.di

import com.example.newsly.data.remote.NewsApiService
import com.example.newsly.data.repository.NewsRepositoryImpl
import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.usecase.GetTopHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Provides
    fun provideGetTopHeadlinesUseCase(repo: NewsRepository) = GetTopHeadlinesUseCase(repo)
}