package ru.project.tutor.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.project.tutor.domain.repository.AnswerChoiceRepository
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.domain.repository.AttemptRepository
import ru.project.tutor.domain.repository.ErrorLogger
import ru.project.tutor.domain.repository.ErrorRepository
import ru.project.tutor.domain.repository.FavoriteRepository
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.repository.AnswerChoiceRepositoryImpl
import ru.project.tutor.repository.AppSharedPreferencesImpl
import ru.project.tutor.repository.AttemptRepositoryImpl
import ru.project.tutor.repository.ErrorRepositoryImpl
import ru.project.tutor.repository.FavoriteRepositoryImpl
import ru.project.tutor.repository.QuestionRepositoryImpl
import ru.project.tutor.repository.TestRepositoryImpl
import ru.project.tutor.utils.AppErrorLogger

val repositoryModule = module {
    single<TestRepository> {
        TestRepositoryImpl(
            testDao = get(),
            questionRepository = get(),
            appSharedPreferences = get()
        )
    }

    single<QuestionRepository> {
        QuestionRepositoryImpl(
            questionDao = get(),
            answerChoiceRepository = get()
        )
    }

    single<AnswerChoiceRepository> {
        AnswerChoiceRepositoryImpl(answerChoiceDao = get())
    }

    single<AttemptRepository> {
        AttemptRepositoryImpl(attemptDao = get())
    }

    single<ErrorRepository> {
        ErrorRepositoryImpl(errorDao = get())
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(favoriteDao = get())
    }

    single<AppSharedPreferences> {
        AppSharedPreferencesImpl(
            errorLogger = get(),
            context = androidContext()
        )
    }

    single<ErrorLogger> { AppErrorLogger }
}
