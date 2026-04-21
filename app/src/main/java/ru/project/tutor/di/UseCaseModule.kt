package ru.project.tutor.di

import org.koin.dsl.module
import ru.project.tutor.domain.usecases.AddAllCountTimeUseCase
import ru.project.tutor.domain.usecases.AllCountErrorsIncrementUseCase
import ru.project.tutor.domain.usecases.AllCountTestsCompletedIncrementUseCase
import ru.project.tutor.domain.usecases.CheckAnswerQuestionUseCase
import ru.project.tutor.domain.usecases.CompletedTestUseCase
import ru.project.tutor.domain.usecases.CreateAttemptUseCase
import ru.project.tutor.domain.usecases.CreateErrorUseCase
import ru.project.tutor.domain.usecases.CreateFavoriteUseCase
import ru.project.tutor.domain.usecases.DeleteErrorByQuestionIdUseCase
import ru.project.tutor.domain.usecases.DeleteFavoriteByQuestionIdUseCase
import ru.project.tutor.domain.usecases.FindNextPendingQuestionIndexUseCase
import ru.project.tutor.domain.usecases.GetAllCountErrorsUseCase
import ru.project.tutor.domain.usecases.GetAllCountTestCreatedUseCase
import ru.project.tutor.domain.usecases.GetAllCountTestsCompletedUseCase
import ru.project.tutor.domain.usecases.GetAllCountTimeUseCase
import ru.project.tutor.domain.usecases.GetDateCreateTestUseCase
import ru.project.tutor.domain.usecases.GetDateLastTakeTestUseCase
import ru.project.tutor.domain.usecases.GetDontShowTooErrorsDialogAgainUseCase
import ru.project.tutor.domain.usecases.GetErrorsByTestIdUseCase
import ru.project.tutor.domain.usecases.GetFavoritesByTestIdUseCase
import ru.project.tutor.domain.usecases.GetFirstLaunchDateUseCase
import ru.project.tutor.domain.usecases.GetQuestionWithAnswersByQuestionIdUseCase
import ru.project.tutor.domain.usecases.GetQuestionsWithAnswersByTestIdUseCase
import ru.project.tutor.domain.usecases.InitFirstLaunchDateUseCase
import ru.project.tutor.domain.usecases.SaveTestSharedDataUseCase
import ru.project.tutor.domain.usecases.SetDateLastTakeTestUseCase
import ru.project.tutor.domain.usecases.SetDontShowTooErrorsDialogAgainUseCase
import ru.project.tutor.domain.usecases.ShuffleAnswersUseCase
import ru.project.tutor.domain.usecases.ShuffleQuestionsUseCase

val useCaseModule = module {
    single { AddAllCountTimeUseCase(get()) }
    single { AllCountErrorsIncrementUseCase(get()) }
    single { AllCountTestsCompletedIncrementUseCase(get()) }
    single { CheckAnswerQuestionUseCase(get(), get(), get()) }
    single { CompletedTestUseCase(get(), get(), get()) }
    single { CreateAttemptUseCase(get()) }
    single { CreateErrorUseCase(get()) }
    single { CreateFavoriteUseCase(get()) }
    single { DeleteErrorByQuestionIdUseCase(get()) }
    single { DeleteFavoriteByQuestionIdUseCase(get()) }
    single { FindNextPendingQuestionIndexUseCase() }
    single { GetAllCountErrorsUseCase(get()) }
    single { GetAllCountTestCreatedUseCase(get()) }
    single { GetAllCountTestsCompletedUseCase(get()) }
    single { GetAllCountTimeUseCase(get()) }
    single { GetDateCreateTestUseCase(get()) }
    single { GetDateLastTakeTestUseCase(get()) }
    single { GetDontShowTooErrorsDialogAgainUseCase(get()) }
    single { GetErrorsByTestIdUseCase(get()) }
    single { GetFavoritesByTestIdUseCase(get()) }
    single { GetFirstLaunchDateUseCase(get()) }
    single { GetQuestionWithAnswersByQuestionIdUseCase(get()) }
    single { GetQuestionsWithAnswersByTestIdUseCase(get()) }
    single { InitFirstLaunchDateUseCase(get()) }
    single { SaveTestSharedDataUseCase(get(), get(), get(), get()) }
    single { SetDateLastTakeTestUseCase(get()) }
    single { SetDontShowTooErrorsDialogAgainUseCase(get()) }
    single { ShuffleAnswersUseCase() }
    single { ShuffleQuestionsUseCase() }
}
