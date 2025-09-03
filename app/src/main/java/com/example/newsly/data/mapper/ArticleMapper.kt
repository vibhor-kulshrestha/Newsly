package com.example.newsly.data.mapper

import com.example.newsly.data.remote.ArticleDto
import com.example.newsly.domain.model.Article

fun ArticleDto.toDomain(): Article {
    return Article(
        title = title?.takeIf { it.isNotBlank() } ?: "No title",
        description = description?.takeIf { it.isNotBlank() } ?: "No description",
        imageUrl = urlToImage.orEmpty(),
        url = url.orEmpty(),
        publishedAt = publishedAt.orEmpty()
    ).apply {

    }
}