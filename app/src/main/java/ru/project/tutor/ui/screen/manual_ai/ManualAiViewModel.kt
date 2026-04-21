package ru.project.tutor.ui.screen.manual_ai

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.R
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.repository.ErrorLogger
import ru.project.tutor.domain.usecases.SaveTestSharedDataUseCase
import ru.project.tutor.utils.ClipboardWrapper
import ru.project.tutor.utils.IntentFileManagerRegister
import ru.project.tutor.utils.createNewTestFromUri
import ru.project.tutor.utils.showToast

class ManualAiViewModel(
    reducer: ManualAiReducer,
    private val clipboardWrapper: ClipboardWrapper,
    private val errorLogger: ErrorLogger,
    private val intentFileManagerRegister: IntentFileManagerRegister,
    private val testSharedUseCase: SaveTestSharedDataUseCase,
    private val context: Context,
) : ScreenViewModel<ManualAiStore.State, ManualAiStore.Event, ManualAiStore.SideEffect, ManualAiStore.Effect, ManualAiStore.UiState>(
    reducer
) {
    override fun createState(): ManualAiStore.State {
        return ManualAiStore.State
    }

    override fun handleEvent(
        currentState: ManualAiStore.State,
        intent: ManualAiStore.Event,
    ): Flow<ManualAiStore.Effect> {
        return when (intent) {
            ManualAiStore.Event.ClickChooseFile -> flow {
                intentFileManagerRegister.launch("application/json") { uri ->
                    viewModelScope.launch {
                        val testId = context.createNewTestFromUri(
                            saveTestSharedDataUseCase = testSharedUseCase,
                            uri = uri
                        ) ?: return@launch
                        AppAnalytics.manualCreateFromFile()
                        sendSideEffect(ManualAiStore.SideEffect.OpenTestManager(testId))
                    }
                }
            }

            ManualAiStore.Event.ClickClose -> flow {
                AppAnalytics.manualClose()
                sendSideEffect(ManualAiStore.SideEffect.Close)
            }

            ManualAiStore.Event.ClickCopyPromtFile -> flow {
                AppAnalytics.manualCopyPromtFile()
                clipboardWrapper.copyToClipboard(PromtData.FILE)
                context.showToast(R.string.copy_promt_file)
            }

            ManualAiStore.Event.ClickCopyPromtUrl -> flow {
                AppAnalytics.manualCopyPromtUrl()
                clipboardWrapper.copyToClipboard(PromtData.URL)
                context.showToast(R.string.copy_promt_file)
            }

            ManualAiStore.Event.ClickDeepSeek -> flow {
                AppAnalytics.manualClickDeepSeek()
                sendSideEffect(ManualAiStore.SideEffect.OpenLink(DEEPSEEK_URL))
            }

            ManualAiStore.Event.ClickInsertBuffer -> flow {
                val json = clipboardWrapper.getFromClipboard()
                val testId = testSharedUseCase.invoke(json)
                if (testId == null) {
                    errorLogger.logMessage("Failed to insert json")
                    context.showToast(R.string.handle_file_json_error_read_json)
                    return@flow
                }
                AppAnalytics.manualCreateFromJson()
                sendSideEffect(ManualAiStore.SideEffect.OpenTestManager(testId))
            }

            ManualAiStore.Event.ClickOpenMovie -> flow {
                AppAnalytics.manualStartMovie()
                sendSideEffect(ManualAiStore.SideEffect.OpenLink(VK_URL))
            }
        }
    }

    override fun handleEffect(
        currentState: ManualAiStore.State,
        effect: ManualAiStore.Effect,
    ): ManualAiStore.State {
        return ManualAiStore.State
    }

    private companion object {
        private const val DEEPSEEK_URL = "https://www.deepseek.com/en"
        private const val VK_URL = "https://vk.com/clip90405070_456240174"
    }

    private object PromtData {
        const val FILE = "Ты — интеллектуальный конвертер тестов в JSON.\n" +
                "Я предоставляю тест в текстовом виде (вопросы и варианты ответов).\n" +
                "\n" +
                "Твоя задача:\n" +
                " 1. Без дополнительных вопросов преобразовать тест в один валидный JSON-файл строго по шаблону ниже.\n" +
                " 2. Если правильные варианты ответа не указаны - самостоятельно определить правильные ответы, ориентируясь на смысл вопроса и текста вариантов.\n" +
                " 3. Допускать вопросы с одним или несколькими правильными ответами.\n" +
                " 4. Если правильный ответ невозможно определить однозначно — выбрать наиболее корректный\n" +
                " 5. Заполнить position последовательно, начиная с 1.\n" +
                " 6. Сохранить исходные формулировки вопросов и ответов без изменений.\n" +
                " 7. Вернуть только JSON, без комментариев, пояснений, markdown и лишнего текста.\n" +
                " 8. Размер теста не ограничен, результат всегда должен быть одним файлом.\n" +
                "       9. Сделай красивый json\n" +
                "10. Если в тексте вопроса или ответа есть символы, которые нужно экранировать в JSON, то сделай это\n" +
                "11. Нужно обработать весь документ\n" +
                "\n" +
                "ФОРМАТ (строго соблюдать):\n" +
                "{\n" +
                "  \"title\": \"Название теста\",\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"question\": \"Текст вопроса\",\n" +
                "      \"position\": 1,\n" +
                "      \"answers\": [\n" +
                "        {\"text\": \"Ответ 1\", \"isRightAnswer\": true},\n" +
                "        {\"text\": \"Ответ 2\", \"isRightAnswer\": false}\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"

        const val URL = "ФОРМАТ выдачи json (строго соблюдать):\n" +
                "{\n" +
                "  \"title\": \"Название теста\",\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"question\": \"Текст вопроса\",\n" +
                "      \"position\": 1,\n" +
                "      \"answers\": [\n" +
                "        {\"text\": \"Ответ 1\", \"isRightAnswer\": true},\n" +
                "        {\"text\": \"Ответ 2\", \"isRightAnswer\": false}\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }
}
