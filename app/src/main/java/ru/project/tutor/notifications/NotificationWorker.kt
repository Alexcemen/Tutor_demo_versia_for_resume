package ru.project.tutor.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.domain.repository.ErrorLogger
import ru.project.tutor.domain.repository.TestRepository
import timber.log.Timber

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val log = Timber.tag(NotificationWorker::class.java.name)
    private val appSharedPreferences: AppSharedPreferences by inject()
    private val testRepository: TestRepository by inject()
    private val notificationHelper: NotificationHelper by inject()
    private val notificationScheduler: NotificationScheduler by inject()
    private val errorLogger: ErrorLogger by inject()

    override suspend fun doWork(): Result {
        return try {
            if (!appSharedPreferences.notificationsEnabled) {
                log.i("Notifications disabled, skipping")
                return Result.success()
            }

            val tests = testRepository.getAllTest().first()
            val testCount = tests.size

            if (testCount == 0) {
                val noTestsMessage = NotificationMessages.getNoTestsMessage()
                notificationHelper.postNotification(
                    noTestsMessage.title,
                    noTestsMessage.body,
                    noTestsMessage.destination.name
                )
                log.i("No tests notification posted")
                return Result.success()
            }

            val errorCount = appSharedPreferences.allCountErrors
            val lastTestName = tests
                .maxByOrNull { it.dateLastTake }
                ?.title
                ?: tests.last().title

            val currentIndex = appSharedPreferences.notificationMessageIndex
            val message = NotificationMessages.get(
                index = currentIndex,
                testCount = testCount,
                errorCount = errorCount,
                lastTestName = lastTestName,
            )

            if (message != null) {
                val destination = message.destination.name
                notificationHelper.postNotification(message.title, message.body, destination)
                log.i("Notification posted: index=$currentIndex, destination=$destination")
            }

            appSharedPreferences.notificationMessageIndex =
                NotificationMessages.nextIndex(currentIndex)

            Result.success()
        } catch (e: Exception) {
            errorLogger.logMessage("NotificationWorker failed: ${e.message}")
            Result.retry()
        }
    }
}
