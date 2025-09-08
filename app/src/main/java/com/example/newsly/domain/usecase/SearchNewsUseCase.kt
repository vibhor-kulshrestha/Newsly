package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.NewsRepository

class SearchNewsUseCase(private val newsRepo : NewsRepository) {
    operator fun invoke(query : String) = newsRepo.searchNews(query)
}