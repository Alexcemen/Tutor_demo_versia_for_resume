package ru.project.tutor.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.project.tutor.ui.MainActivityViewModel
import ru.project.tutor.ui.screen.ad_explanation.AdExplanationReducer
import ru.project.tutor.ui.screen.ad_explanation.AdExplanationViewModel
import ru.project.tutor.ui.screen.bottom_navigation.BottomNavigationReducer
import ru.project.tutor.ui.screen.bottom_navigation.BottomNavigationViewModel
import ru.project.tutor.ui.screen.card_info.CardInfoReducer
import ru.project.tutor.ui.screen.card_info.CardInfoViewModel
import ru.project.tutor.ui.screen.loading.SplashReducer
import ru.project.tutor.ui.screen.loading.SplashViewModel
import ru.project.tutor.ui.screen.manual_ai.ManualAiReducer
import ru.project.tutor.ui.screen.manual_ai.ManualAiViewModel
import ru.project.tutor.ui.screen.onboarding.OnboardingReducer
import ru.project.tutor.ui.screen.onboarding.OnboardingViewModel
import ru.project.tutor.ui.screen.question_factory.QuestionFactoryReducer
import ru.project.tutor.ui.screen.question_factory.QuestionFactoryViewModel
import ru.project.tutor.ui.screen.questions_list.QuestionsListReducer
import ru.project.tutor.ui.screen.questions_list.QuestionsListViewModel
import ru.project.tutor.ui.screen.starting_testing.StartingTestingReducer
import ru.project.tutor.ui.screen.starting_testing.StartingTestingViewModel
import ru.project.tutor.ui.screen.test_factory.TestFactoryReducer
import ru.project.tutor.ui.screen.test_factory.TestFactoryViewModel
import ru.project.tutor.ui.screen.test_info.TestInfoReducer
import ru.project.tutor.ui.screen.test_info.TestInfoViewModel
import ru.project.tutor.ui.screen.test_manager.TestManagerReducer
import ru.project.tutor.ui.screen.test_manager.TestManagerViewModel
import ru.project.tutor.ui.screen.test_process.TestProcessReducer
import ru.project.tutor.ui.screen.test_process.TestProcessViewModel
import ru.project.tutor.ui.screen.test_result.TestResultReducer
import ru.project.tutor.ui.screen.test_result.TestResultViewModel
import ru.project.tutor.ui.screen.tests_list.tests_list.TestsListReducer
import ru.project.tutor.ui.screen.tests_list.tests_list.TestsListViewModel

val viewModelModule = module {
    // MainActivityViewModel
    viewModel { MainActivityViewModel(saveTestSharedDataUseCase = get(), context = get()) }

    // Reducers (stateless, можно использовать single)
    single { BottomNavigationReducer() }
    single { CardInfoReducer(localeManager = get()) }
    single { SplashReducer() }
    single { ManualAiReducer() }
    single { OnboardingReducer() }
    single { AdExplanationReducer() }
    single { QuestionFactoryReducer() }
    single { QuestionsListReducer() }
    single { StartingTestingReducer() }
    single { TestFactoryReducer() }
    single { TestInfoReducer() }
    single { TestManagerReducer() }
    single { TestProcessReducer() }
    single { TestResultReducer() }
    single { TestsListReducer() }

    // ViewModels без assisted параметров
    viewModel { BottomNavigationViewModel(reducer = get(), appResource = get()) }
    viewModel {
        CardInfoViewModel(
            reducer = get(),
            appSharedPreferences = get(),
            notificationScheduler = get(),
            debugTestFactory = get(),
            localeManager = get(),
            context = get()
        )
    }
    viewModel {
        SplashViewModel(
            reducer = get(),
            getFirstLaunchDateUseCase = get(),
            getAllCountTestCreatedUseCase = get(),
            getAllCountErrorsUseCase = get(),
            getAllCountTestsCompletedUseCase = get(),
            getAllCountTimeUseCase = get(),
            initFirstLaunchDateUseCase = get(),
            appSharedPreferences = get()
        )
    }
    viewModel {
        ManualAiViewModel(
            reducer = get(),
            clipboardWrapper = get(),
            errorLogger = get(),
            intentFileManagerRegister = get(),
            testSharedUseCase = get(),
            context = get()
        )
    }
    viewModel { OnboardingViewModel(reducer = get(), appSharedPreferences = get()) }
    viewModel { AdExplanationViewModel(reducer = get()) }
    viewModel {
        TestFactoryViewModel(
            reducer = get(),
            testRepository = get(),
            intentFileManagerRegister = get(),
            testSharedUseCase = get(),
            context = get()
        )
    }
    viewModel {
        TestInfoViewModel(
            reducer = get(),
            testRepository = get(),
            getFirstLaunchDateUseCase = get(),
            getAllCountTestCreatedUseCase = get(),
            getAllCountErrorsUseCase = get(),
            getAllCountTestsCompletedUseCase = get(),
            getAllCountTimeUseCase = get(),
            reviewStarterSharedFlow = get(),
            appSharedPreferences = get()
        )
    }

    // ViewModels с assisted параметрами
    viewModel { (testId: Int, questionId: Int) ->
        QuestionFactoryViewModel(
            reducer = get(),
            questionRepository = get(),
            answerChoiceRepository = get(),
            appResource = get(),
            testId = testId,
            questionId = questionId
        )
    }

    viewModel { (testId: Int, testsListType: ru.project.tutor.ui.screen.test_info.TestsListType) ->
        QuestionsListViewModel(
            reducer = get(),
            questionRepository = get(),
            favoriteRepository = get(),
            errorRepository = get(),
            testId = testId,
            testsListType = testsListType
        )
    }

    viewModel { (testId: Int, typeTest: ru.project.tutor.ui.screen.test_info.TestsListType) ->
        StartingTestingViewModel(
            reducer = get(),
            testRepository = get(),
            questionRepository = get(),
            favoriteRepository = get(),
            errorRepository = get(),
            appResource = get(),
            testId = testId,
            typeTest = typeTest
        )
    }

    viewModel { (testId: Int) ->
        TestManagerViewModel(
            reducer = get(),
            testRepository = get(),
            questionRepository = get(),
            context = get(),
            testId = testId
        )
    }

    viewModel { (testMode: ru.project.tutor.ui.screen.test_process.TestMode, startingMode: ru.project.tutor.ui.screen.test_info.TestsListType, testId: Int, options: ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi) ->
        TestProcessViewModel(
            reducer = get(),
            checkAnswerQuestionUseCase = get(),
            adStarterSharedFlow = get(),
            interstitialResultListener = get(),
            bannerAdLoader = get(),
            createAttemptUseCase = get(),
            getQuestionsWithAnswersByTestIdUseCase = get(),
            getQuestionWithAnswersByQuestionIdUseCase = get(),
            getFavoritesByTestIdUseCase = get(),
            deleteFavoriteByQuestionIdUseCase = get(),
            createFavoriteUseCase = get(),
            getErrorsByTestIdUseCase = get(),
            shuffleQuestionsUseCase = get(),
            shuffleAnswersUseCase = get(),
            findNextPendingQuestionIndexUseCase = get(),
            completedTestUseCase = get(),
            errorLogger = get(),
            testMode = testMode,
            startingMode = startingMode,
            testId = testId,
            options = options
        )
    }

    viewModel { (testId: Int, attemptId: Int) ->
        TestResultViewModel(
            reducer = get(),
            attemptRepository = get(),
            adStarterSharedFlow = get(),
            resultListener = get(),
            setDontShowTooErrorsDialogAgainUseCase = get(),
            getDontShowTooErrorsDialogAgainUseCase = get(),
            errorLogger = get(),
            bannerAdLoader = get(),
            testId = testId,
            attemptId = attemptId
        )
    }

    viewModel { (testsListType: ru.project.tutor.ui.screen.test_info.TestsListType) ->
        TestsListViewModel(
            reducer = get(),
            testRepository = get(),
            testsListType = testsListType
        )
    }
}
