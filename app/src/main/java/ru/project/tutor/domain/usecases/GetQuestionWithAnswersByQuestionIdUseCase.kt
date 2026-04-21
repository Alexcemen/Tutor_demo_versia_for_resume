package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.utils.withIO

class GetQuestionWithAnswersByQuestionIdUseCase(
    private val questionRepository: QuestionRepository,
) {
    suspend operator fun invoke(
        questionId: Int,
    ): QuestionWithAnswersData? = withIO {
        questionRepository.getQuestionWithAnswersByQuestionId(
            questionId = questionId
        )
    }
}