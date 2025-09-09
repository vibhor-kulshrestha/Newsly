package com.example.newsly.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsly.data.local.NewsDatabase
import com.example.newsly.data.local.entity.toEntity
import com.example.newsly.data.mapper.toDomain
import com.example.newsly.data.remote.NewsApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NewsSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val newsApi: NewsApiService,
    private val db: NewsDatabase,
) : CoroutineWorker(
    context,
    workerParams
) {
    override suspend fun doWork(): Result {
        return try {
            val response = newsApi.getTopHeadlines("us", 1, 20)
            db.withTransaction {
                db.articleDao().clearAll()
                db.articleDao().insertArticles(response.articles.map { it.toDomain().toEntity() })
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}