package com.example.newsly.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsly.data.remote.ArticleDto
import com.example.newsly.data.remote.NewsApiService

class SearchNewsPagingSource(
    private val newsApiService: NewsApiService,
    private val query: String
) : PagingSource<Int, ArticleDto>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.searchNews(query, page, params.loadSize)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}