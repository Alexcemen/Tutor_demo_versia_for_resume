package ru.project.tutor.ui.navigation

import kotlinx.serialization.Serializable
import ru.project.tutor.common_ui.composable.mvi.AppNavKey
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_process.TestMode

@Serializable
data class TestFactory(override val type: String = TestFactory::class.simpleName.toString()) :
    AppNavKey()

@Serializable
data class TestManager(
    override val type: String = TestManager::class.simpleName.toString(),
    val testId: Int,
) :
    AppNavKey()

@Serializable
data class QuestionFactory(
    override val type: String = QuestionFactory::class.simpleName.toString(),
    val testId: Int,
    val questionId: Int,
) : AppNavKey()

@Serializable
data class BottomNavigation(override val type: String = BottomNavigation::class.simpleName.toString()) :
    AppNavKey()

@Serializable
data class SplashScreen(override val type: String = SplashScreen::class.simpleName.toString()) :
    AppNavKey()

@Serializable
data class StartingTesting(
    override val type: String = StartingTesting::class.simpleName.toString(),
    val testId: Int,
    val mode: TestsListType,
) : AppNavKey()

@Serializable
data class TestProcess(
    override val type: String = TestProcess::class.simpleName.toString(),
    val testMode: TestMode,
    val startingMode: TestsListType,
    val testId: Int,
    val options: OptionsStartTestingUi,
) : AppNavKey()

@Serializable
data class TestResult(
    override val type: String = TestResult::class.simpleName.toString(),
    val testId: Int,
    val attemptId: Int,
) : AppNavKey()

@Serializable
data class FavoritesList(override val type: String = FavoritesList::class.simpleName.toString()) :
    AppNavKey()

@Serializable
data class QuestionsList(
    override val type: String = QuestionsList::class.simpleName.toString(),
    val testId: Int,
    val testsListType: TestsListType,
) : AppNavKey()

@Serializable
data class TestsList(
    override val type: String = TestsList::class.simpleName.toString(),
    val testsListType: TestsListType,
) : AppNavKey()

@Serializable
data class ManualAi(
    override val type: String = ManualAi::class.simpleName.toString(),
) : AppNavKey()

@Serializable
data class Onboarding(
    override val type: String = Onboarding::class.simpleName.toString(),
) : AppNavKey()

@Serializable
data class AdExplanation(
    override val type: String = AdExplanation::class.simpleName.toString(),
) : AppNavKey()