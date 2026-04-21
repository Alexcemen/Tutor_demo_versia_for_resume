package ru.project.tutor.ui.models.options_for_testing

import kotlinx.serialization.Serializable

@Serializable
data class OptionsStartTestingUi(
    val isTimerEnabled: Boolean,
    val durationTesting: Int,
    val shuffleQuestions: Boolean,
    val shuffleAnswers: Boolean,
    val showRightAnswer: Boolean,
    val isRandomQuestionsEnable: Boolean,
    val countRandomQuestions: Int,
    val doNotMarkMultipleAnswerChoice: Boolean,
)