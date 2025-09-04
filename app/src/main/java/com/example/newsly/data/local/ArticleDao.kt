package com.example.newsly.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsly.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * FROM ArticleEntity ORDER BY publishedAt DESC")
    fun getArticles(): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM ArticleEntity")
    suspend fun clearAll()
}