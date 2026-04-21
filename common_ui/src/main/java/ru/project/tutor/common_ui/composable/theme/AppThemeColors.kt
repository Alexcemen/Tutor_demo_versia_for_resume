package ru.project.tutor.common_ui.composable.theme

import androidx.compose.ui.graphics.Color

interface AppThemeColors {
    interface Text {
        val primary: Color
        val placeholder: Color
        val red: Color
        val blue: Color
        val primaryUniform: Color
        val reverse: Color
        val blueTwo: Color
        val whiteUniform: Color
        val reversed: Color
    }

    interface Background {
        val basic: Color
        val blue: Color
        val red: Color
        val green: Color
        val secondary: Color
        val secondaryTwo: Color
        val primary: Color
        val basicUniform: Color
        val mask: Color
        val yellow: Color
        val blueLight: Color
    }

    val text: Text
    val background: Background
}


class AppThemeColorsSchemes(
    val dark: AppThemeColors,
    val light: AppThemeColors,
    private val current: AppThemeColors,
) : AppThemeColors by current