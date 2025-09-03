package com.example.newsly.domain.usecase

import androidx.paging.PagingData
import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetTopHeadlinesUseCase(private val repository: NewsRepository) {
    operator fun invoke(country: String): Flow<PagingData<Article>> = repository.getTopHeadlines(country)
}