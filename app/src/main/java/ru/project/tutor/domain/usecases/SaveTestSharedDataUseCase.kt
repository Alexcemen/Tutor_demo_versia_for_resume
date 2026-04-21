package ru.project.tutor.domain.usecases

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import ru.project.tutor.domain.models.TestShareData
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.repository.AnswerChoiceRepository
import ru.project.tutor.domain.repository.ErrorLogger
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.utils.withIO
import timber.log.Timber

class SaveTestSharedDataUseCase(
    private val testRepository: TestRepository,
    private val questionRepository: QuestionRepository,
    private val answerChoiceRepository: AnswerChoiceRepository,
    private val errorLogger: ErrorLogger,
) {
    private val jsonBuilder = Json { prettyPrint = true }

    suspend operator fun invoke(jsonData: String): Int? = withIO {
        Timber.d("parse json = $jsonData")
        val dataShared = decode(jsonData) ?: return@withIO null
        val testId = testRepository.createTest(dataShared.title)
        dataShared.questions.forEach {
            val questionId = questionRepository.createQuestion(
                question = it.question,
                testId = testId
            )
            answerChoiceRepository.createAnswerChoiceList(
                answers = it.answers.map { answer ->
                    AnswerChoiceData(
                        id = 0,
                        text = answer.text,
                        isRightAnswer = answer.isRightAnswer,
                        questionId = questionId
                    )
                }
            )
        }
        return@withIO testId
    }

    private fun decode(jsonData: String): TestShareData? {
        return try {
            jsonBuilder.decodeFromString<TestShareData>(jsonData)
        } catch (e: SerializationException) {
            errorLogger.logException(e)
            null
        } catch (e: IllegalArgumentException) {
            errorLogger.logException(e)
            null
        }
    }
}