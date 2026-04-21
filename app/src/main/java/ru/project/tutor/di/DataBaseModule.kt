package ru.project.tutor.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.project.tutor.database.AppDatabase
import ru.project.tutor.database.createDatabase

val dataBaseModule = module {
    single<AppDatabase> { createDatabase(androidContext()) }
    factory { get<AppDatabase>().testDao() }
    factory { get<AppDatabase>().questionDao() }
    factory { get<AppDatabase>().answerChoiceDao() }
    factory { get<AppDatabase>().attemptDao() }
    factory { get<AppDatabase>().errorDao() }
    factory { get<AppDatabase>().favoriteDao() }
}
