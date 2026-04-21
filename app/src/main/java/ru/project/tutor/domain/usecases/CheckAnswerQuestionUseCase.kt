package ru.project.tutor.domain.usecases

import ru.project.tutor.ui.models.QuestionForTestProcessUi
import ru.project.tutor.ui.screen.test_process.composable.QuestionStatus

class CheckAnswerQuestionUseCase(
    private val createErrorUseCase: CreateErrorUseCase,
    private val allCountErrorsIncrementUseCase: AllCountErrorsIncrementUseCase,
    private val deleteErrorByQuestionIdUseCase: DeleteErrorByQuestionIdUseCase,
) {
    suspend operator fun invoke(
        testId: Int,
        question: QuestionForTestProcessUi,
    ): QuestionForTestProcessUi {
        val checked = evaluateAnswer(question)

        if (checked.questionStatus == QuestionStatus.ERROR) {
            createErrorUseCase(testId = testId, questionId = checked.question.id)
            allCountErrorsIncrementUseCase()
        } else {
            deleteErrorByQuestionIdUseCase(questionId = checked.question.id)
        }

        return checked
    }

    private fun evaluateAnswer(
        question: QuestionForTestProcessUi,
    ): QuestionForTestProcessUi {
        val hasMistake = question.answers.any { answer ->
            val isRight = answer.answerChoiceData.isRightAnswer
            val isSelected = answer.isSelectedAnswer
            (isRight && !isSelected) || (!isRight && isSelected)
        }

        val status = if (hasMistake) QuestionStatus.ERROR else QuestionStatus.CORRECT

        return question.copy(
            questionStatus = status,
            isQuestionAnsweredBefore = true,
        )
    }
}
