package ru.project.tutor.ui.screen.test_result.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme

enum class QuestionStatusTestResult {
    ERROR, CORRECT;

    @Composable
    fun colorAnswerBackground(
        isSelectedAnswer: Boolean,
        isRightAnswer: Boolean,
    ): Color {
        return when {
            this == ERROR && isSelectedAnswer && !isRightAnswer -> AppTheme.colors.background.red
            this == ERROR && isSelectedAnswer && isRightAnswer -> AppTheme.colors.background.green
            this == CORRECT && isSelectedAnswer && isRightAnswer -> AppTheme.colors.background.green
            else -> AppTheme.colors.background.mask
        }
    }

    @Composable
    fun colorAnswerBorder(
        isSelectedAnswer: Boolean,
        isRightAnswer: Boolean,
    ): Color {
        return when {
            this == ERROR && isSelectedAnswer && !isRightAnswer -> AppTheme.colors.background.red
            this == ERROR && isSelectedAnswer && isRightAnswer -> AppTheme.colors.background.green
            this == ERROR && isRightAnswer -> AppTheme.colors.background.green
            this == CORRECT && isRightAnswer -> AppTheme.colors.background.green
            else -> AppTheme.colors.background.secondaryTwo
        }
    }

    @Composable
    fun painterIcon(
        isSelectedIcon: Boolean,
        isRightAnswer: Boolean,
        isMultipleAnswerChoice: Boolean,
    ): Int {
        if (isMultipleAnswerChoice) {
            return when {
                this == ERROR && isRightAnswer -> R.drawable.ic_square_checkmark_correct
                isSelectedIcon -> R.drawable.ic_square_checkmark_correct
                else -> R.drawable.ic_square_checkmark_incorrect
            }
        } else {
            return when {
                this == ERROR && isRightAnswer -> R.drawable.ic_correct_choice
                isSelectedIcon -> R.drawable.ic_correct_choice
                else -> R.drawable.ic_incorrect_choice
            }
        }
    }
}