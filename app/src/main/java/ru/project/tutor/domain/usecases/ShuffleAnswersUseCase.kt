package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData

class ShuffleAnswersUseCase {
    operator fun invoke(
        questionsWithAnswers: List<QuestionWithAnswersData>,
    ): List<QuestionWithAnswersData> {
        return questionsWithAnswers.map {
            it.copy(answers = it.answers.shuffled())
        }
    }
}