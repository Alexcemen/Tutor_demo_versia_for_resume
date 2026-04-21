package ru.project.tutor

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ru.project.tutor.di.appModule
import ru.project.tutor.di.dataBaseModule
import ru.project.tutor.di.repositoryModule
import ru.project.tutor.di.useCaseModule
import ru.project.tutor.di.viewModelModule
import ru.project.tutor.domain.LocaleManager
import ru.project.tutor.notifications.NotificationHelper

class TutorApplication : Application(), Configuration.Provider {

    private lateinit var notificationHelper: NotificationHelper

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().build()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TutorApplication)
            modules(
                dataBaseModule,
                repositoryModule,
                appModule,
                useCaseModule,
                viewModelModule
            )
        }

        notificationHelper = getKoin().get<NotificationHelper>()
        val localeManager = getKoin().get<LocaleManager>()
        localeManager.applyLocale(this)
        notificationHelper.createChannel()

        // Инициализируем WorkManager вручную, т.к. WorkManagerInitializer отключен в манифесте
        WorkManager.initialize(this, workManagerConfiguration)
    }
}
