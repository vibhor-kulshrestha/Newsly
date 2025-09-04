package com.example.newsly.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsly.data.local.NewsDatabase
import com.example.newsly.data.local.entity.ArticleEntity
import com.example.newsly.data.local.entity.toEntity
import com.example.newsly.data.mapper.toDomain
import com.example.newsly.data.remote.NewsApiService

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApi: NewsApiService,
    private val newsDatabase: NewsDatabase,
    private val countryCode: String
) : RemoteMediator<Int, ArticleEntity>() {
    var pageNo : Int = 1;
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> pageNo = 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    } else (pageNo++)
                }
            }

            val response = newsApi.getTopHeadlines(countryCode, pageNo, state.config.pageSize)

            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDatabase.articleDao().clearAll()
                }
                newsDatabase.articleDao()
                    .insertArticles(response.articles.map { it.toDomain().toEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = response.articles.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}