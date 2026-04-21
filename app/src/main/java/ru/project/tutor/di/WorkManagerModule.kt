package ru.project.tutor.di

import androidx.work.WorkerParameters
import org.koin.dsl.module
import ru.project.tutor.notifications.NotificationWorker

val workManagerModule = module {
    factory { (context: android.content.Context, params: WorkerParameters) ->
        NotificationWorker(context, params)
    }
}
