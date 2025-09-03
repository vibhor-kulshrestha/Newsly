package com.example.newsly.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsly.data.remote.ArticleDto
import com.example.newsly.data.remote.NewsApiService

class NewsPagingSource(
    private val api: NewsApiService,
    private val country: String
) : PagingSource<Int, ArticleDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        return try {
            val page = params.key ?: 1
            val response = api.getTopHeadlines(country, page, params.loadSize)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}