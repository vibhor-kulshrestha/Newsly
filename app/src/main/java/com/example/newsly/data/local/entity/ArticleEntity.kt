package com.example.newsly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsly.domain.model.Article

@Entity
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val publishedAt: String
)

fun ArticleEntity.toDomain(): Article = Article(
    title = title,
    description = description,
    imageUrl = imageUrl,
    url = url,
    publishedAt = publishedAt
)

fun Article.toEntity(): ArticleEntity = ArticleEntity(
    url = url,
    title = title,
    description = description,
    imageUrl = imageUrl,
    publishedAt = publishedAt
)
