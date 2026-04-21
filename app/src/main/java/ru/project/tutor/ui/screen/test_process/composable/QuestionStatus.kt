package ru.project.tutor.ui.screen.test_process.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.project.tutor.common_ui.composable.theme.AppTheme

enum class QuestionStatus {
    ERROR, CORRECT, UNRESOLVED;

    @Composable
    fun colorIconBackground(
        isSelectedIcon: Boolean,
        showRightAnswer: Boolean,
    ): Color =
        if (isSelectedIcon) {
            AppTheme.colors.background.primary
        } else if (showRightAnswer) {
            when (this) {
                ERROR -> AppTheme.colors.background.red
                CORRECT -> AppTheme.colors.background.green
                UNRESOLVED -> AppTheme.colors.background.secondaryTwo
            }
        } else {
            when (this) {
                ERROR -> AppTheme.colors.background.blue
                CORRECT -> AppTheme.colors.background.blue
                UNRESOLVED -> AppTheme.colors.background.secondaryTwo
            }
        }

    @Composable
    fun colorBorder(
        isSelectedIcon: Boolean,
        showRightAnswer: Boolean,
    ): Color =
        if (isSelectedIcon) {
            AppTheme.colors.background.primary
        } else if (showRightAnswer) {
            when (this) {
                ERROR -> AppTheme.colors.background.red
                CORRECT -> AppTheme.colors.background.green
                UNRESOLVED -> AppTheme.colors.background.primary
            }
        } else {
            when (this) {
                ERROR -> AppTheme.colors.background.blue
                CORRECT -> AppTheme.colors.background.blue
                UNRESOLVED -> AppTheme.colors.background.primary
            }
        }

    @Composable
    fun colorIconText(
        isSelectedIcon: Boolean,
    ): Color =
        if (isSelectedIcon) {
            AppTheme.colors.text.reverse
        } else {
            when (this) {
                ERROR -> AppTheme.colors.text.whiteUniform
                CORRECT -> AppTheme.colors.text.whiteUniform
                UNRESOLVED -> AppTheme.colors.text.primary
            }
        }

    @Composable
    fun colorAnswerBackground(
        showRightAnswer: Boolean,
        isSelectedAnswer: Boolean,
    ): Color {
        return if (showRightAnswer) {
            when {
                isSelectedAnswer -> AppTheme.colors.background.primary
                else -> AppTheme.colors.background.mask
            }
        } else {
            when {
                this == UNRESOLVED && isSelectedAnswer -> AppTheme.colors.background.primary
                isSelectedAnswer -> AppTheme.colors.background.blue
                else -> AppTheme.colors.background.mask
            }
        }
    }

    @Composable
    fun colorAnswerBorder(
        showRightAnswer: Boolean,
        isSelectedAnswer: Boolean,
        isRightAnswer: Boolean,
    ): Color {
        return if (showRightAnswer) {
            when {
                this == ERROR && isSelectedAnswer && !isRightAnswer -> AppTheme.colors.background.red
                this == ERROR && isSelectedAnswer && isRightAnswer -> AppTheme.colors.background.green
                this == ERROR && isRightAnswer -> AppTheme.colors.background.green
                this == CORRECT && isRightAnswer -> AppTheme.colors.background.green
                this == UNRESOLVED && isSelectedAnswer -> AppTheme.colors.background.primary
                else -> AppTheme.colors.background.secondaryTwo
            }
        } else {
            when {
                this == UNRESOLVED && isSelectedAnswer -> AppTheme.colors.background.primary
                isSelectedAnswer -> AppTheme.colors.background.blue
                else -> AppTheme.colors.background.secondaryTwo
            }
        }
    }
}