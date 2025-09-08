package com.example.newsly.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsly.data.local.NewsDatabase
import com.example.newsly.data.local.entity.toDomain
import com.example.newsly.data.mapper.toDomain
import com.example.newsly.data.paging.NewsRemoteMediator
import com.example.newsly.data.paging.SearchNewsPagingSource
import com.example.newsly.data.remote.NewsApiService
import com.example.newsly.domain.model.Article
import com.example.newsly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val api: NewsApiService,
    private val db: NewsDatabase
) : NewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlines(country: String): Flow<PagingData<Article>> {
        val pagingSourceFactory = { db.articleDao().getArticles() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NewsRemoteMediator(api, db, country),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchNewsPagingSource(api, query) }
        ).flow.map { pagingData ->
            pagingData.map { dto ->
                dto.toDomain()
            }
        }
    }
}