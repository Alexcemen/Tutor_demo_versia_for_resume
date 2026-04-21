package ru.project.tutor.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class NotificationScheduler(
    private val context: Context,
) {
    companion object {
        const val WORK_TAG = "re_engagement_notification"
        private const val REPEAT_INTERVAL_DAYS = 1L
    }

    fun schedule() {
        val request = PeriodicWorkRequestBuilder<NotificationWorker>(
            REPEAT_INTERVAL_DAYS,
            TimeUnit.DAYS
        )
            .addTag(WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancel() {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_TAG)
    }
}
