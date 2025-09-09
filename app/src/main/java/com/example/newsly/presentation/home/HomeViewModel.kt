package com.example.newsly.presentation.home

import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newsly.data.work.NewsSyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.util.concurrent.TimeUnit

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {
    fun scheduleSync() {
        val request = PeriodicWorkRequestBuilder<NewsSyncWorker>(6, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        workManager.enqueueUniquePeriodicWork(
            "NewsSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}