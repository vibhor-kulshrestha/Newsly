package com.example.newsly.domain.model

data class Article(
    val title: String,
    val description: String,
    val imageUrl: String,
    val url: String,
    val publishedAt: String
)
