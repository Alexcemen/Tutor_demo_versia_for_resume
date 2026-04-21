package ru.project.tutor.ui.screen.test_factory

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.domain.usecases.SaveTestSharedDataUseCase
import ru.project.tutor.utils.IntentFileManagerRegister
import ru.project.tutor.utils.createNewTestFromUri
import ru.project.tutor.utils.withIO

class TestFactoryViewModel(
    reducer: TestFactoryReducer,
    private val testRepository: TestRepository,
    private val intentFileManagerRegister: IntentFileManagerRegister,
    private val testSharedUseCase: SaveTestSharedDataUseCase,
    private val context: Context,
) : ScreenViewModel<TestFactoryStore.State, TestFactoryStore.Event, TestFactoryStore.SideEffect, TestFactoryStore.Effect, TestFactoryStore.UiState>(
    reducer
) {
    override fun createState(): TestFactoryStore.State = TestFactoryStore.State()
    override fun handleEffect(
        currentState: TestFactoryStore.State,
        effect: TestFactoryStore.Effect,
    ): TestFactoryStore.State = when (effect) {
        is TestFactoryStore.Effect.UpdateText -> {
            currentState.copy(nameTest = effect.nameTest)
        }
    }

    override fun handleEvent(
        currentState: TestFactoryStore.State,
        intent: TestFactoryStore.Event,
    ): Flow<TestFactoryStore.Effect> {
        return when (intent) {
            TestFactoryStore.Event.Close -> flow {
                sendSideEffect(TestFactoryStore.SideEffect.Close)
            }

            TestFactoryStore.Event.Save -> flow {
                if (currentState.nameTest.isEmpty() ||
                    currentState.nameTest.isBlank()
                ) {
                    sendSideEffect(TestFactoryStore.SideEffect.ShowEmptyDataError)
                    return@flow
                }
                val testId = withIO {
                    testRepository.createTest(
                        currentState.nameTest
                    )
                }
                AppAnalytics.testCreated(currentState.nameTest)
                sendSideEffect(TestFactoryStore.SideEffect.OpenTestManager(testId))
            }

            is TestFactoryStore.Event.UpdateText -> flow {
                emit(TestFactoryStore.Effect.UpdateText(nameTest = intent.text))
            }

            TestFactoryStore.Event.OpenFileManager -> flow {
                intentFileManagerRegister.launch("application/json") { uri ->
                    viewModelScope.launch {
                        val testId = context.createNewTestFromUri(
                            saveTestSharedDataUseCase = testSharedUseCase,
                            uri = uri
                        ) ?: return@launch
                        AppAnalytics.testCreatedFile()
                        sendSideEffect(TestFactoryStore.SideEffect.OpenTestManager(testId))
                    }
                }
            }

            TestFactoryStore.Event.ClickManualAi -> flow {
                AppAnalytics.clickManual()
                sendSideEffect(TestFactoryStore.SideEffect.OpenManualAi)
            }
        }
    }

}