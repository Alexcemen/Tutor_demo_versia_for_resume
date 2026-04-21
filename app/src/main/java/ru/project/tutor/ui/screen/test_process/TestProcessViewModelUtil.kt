package ru.project.tutor.ui.screen.test_process

import ru.project.tutor.domain.models.favorite.FavoriteData
import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionForTestProcessUi
import ru.project.tutor.ui.screen.test_process.composable.QuestionStatus

object TestProcessViewModelUtil {

    fun mapToQuestionUiList(
        questionsWithAnswers: List<QuestionWithAnswersData>,
        favorites: List<FavoriteData>,
    ): List<QuestionForTestProcessUi> {
        val favoriteIds = favorites.map { it.questionId }.toSet()

        return questionsWithAnswers.map {
            QuestionForTestProcessUi(
                question = it.question,
                answers = it.answers.map { answer ->
                    AnswerChoiceDataUi(
                        answerChoiceData = answer
                    )
                },
                questionStatus = QuestionStatus.UNRESOLVED,
                isQuestionAnsweredBefore = false,
                isFavorite = favoriteIds.contains(it.question.id),
                isMultipleAnswerChoice = it.question.isMultipleAnswerChoice
            )
        }
    }

    fun updateAnsweredQuestion(
        question: QuestionForTestProcessUi,
        selectedAnswer: AnswerChoiceDataUi,
        doNotMarkMultipleAnswerChoice: Boolean,
    ): QuestionForTestProcessUi {
        if (question.isMultipleAnswerChoice || doNotMarkMultipleAnswerChoice) {
            return question.copy(
                answers = question.answers.map { answer ->
                    if (answer.answerChoiceData.id == selectedAnswer.answerChoiceData.id)
                        answer.copy(isSelectedAnswer = !answer.isSelectedAnswer)
                    else answer
                }
            )
        } else {
            return question.copy(
                answers = question.answers.map { answer ->
                    if (answer.answerChoiceData.id == selectedAnswer.answerChoiceData.id)
                        answer.copy(isSelectedAnswer = !answer.isSelectedAnswer)
                    else answer.copy(isSelectedAnswer = false)
                }
            )
        }
    }

    fun List<QuestionForTestProcessUi>.replaceById(
        updatedQuestion: QuestionForTestProcessUi,
    ): List<QuestionForTestProcessUi> {
        return map { if (it.question.id == updatedQuestion.question.id) updatedQuestion else it }
    }

    fun TestProcessStore.State.copyCommon(
        numberSelectedQuestion: Int = this.numberSelectedQuestion,
        durationTesting: Int = this.durationTesting,
        questions: List<QuestionForTestProcessUi> = this.questions,
        showRightAnswerOption: Boolean = (this as? TestProcessStore.WorkoutState)?.showRightAnswerOption == true,
        tagMultipleAnswerChoice: Boolean = (this as? TestProcessStore.WorkoutState)?.doNotMarkMultipleAnswerChoice == true,
        showConfirmExitBottomSheet: Boolean = when (this) {
            is TestProcessStore.WorkoutState -> this.showConfirmExitBottomSheet
            is TestProcessStore.ExamState -> this.showConfirmExitBottomSheet
        },
    ): TestProcessStore.State = when (this) {
        is TestProcessStore.WorkoutState -> this.copy(
            testId = testId,
            numberSelectedQuestion = numberSelectedQuestion,
            durationTesting = durationTesting,
            questions = questions,
            showRightAnswerOption = showRightAnswerOption,
            doNotMarkMultipleAnswerChoice = tagMultipleAnswerChoice,
            showConfirmExitBottomSheet = showConfirmExitBottomSheet
        )

        is TestProcessStore.ExamState -> this.copy(
            testId = testId,
            numberSelectedQuestion = numberSelectedQuestion,
            durationTesting = durationTesting,
            questions = questions,
            showConfirmExitBottomSheet = showConfirmExitBottomSheet
        )
    }
}
