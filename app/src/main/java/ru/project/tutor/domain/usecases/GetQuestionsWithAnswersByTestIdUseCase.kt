package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.utils.withIO

class GetQuestionsWithAnswersByTestIdUseCase(
    private val questionRepository: QuestionRepository,
) {
    suspend operator fun invoke(
        testId: Int,
    ): List<QuestionWithAnswersData> = withIO {
        questionRepository.getQuestionsWithAnswersByTestId(
            testId = testId
        )
    }
}