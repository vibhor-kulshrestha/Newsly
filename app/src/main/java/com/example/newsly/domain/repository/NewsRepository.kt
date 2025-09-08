package com.example.newsly.domain.repository

import androidx.paging.PagingData
import com.example.newsly.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(country: String): Flow<PagingData<Article>>
    fun searchNews(query : String) : Flow<PagingData<Article>>
}