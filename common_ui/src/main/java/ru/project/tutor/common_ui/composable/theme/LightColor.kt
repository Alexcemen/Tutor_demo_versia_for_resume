package ru.project.tutor.common_ui.composable.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
object LightText : AppThemeColors.Text {
    override val primary: Color
        get() = Color(0xFF1C1C1C)
    override val placeholder: Color
        get() = Color(0xFF808080)
    override val red: Color
        get() = Color(0xFFD45858)
    override val blue: Color
        get() = Color(0xFF588BD4)
    override val primaryUniform: Color
        get() = Color(0xFF1C1C1C)
    override val reverse: Color
        get() = Color(0xFFFBFBFB)
    override val blueTwo: Color
        get() = Color(0xFF286BAF)
    override val whiteUniform: Color
        get() = Color(0xFFFFFFFF)
    override val reversed: Color
        get() = Color(0xFF1C1C1C)
}

@Immutable
object LightBackground : AppThemeColors.Background {
    override val basic: Color
        get() = Color(0xFFFFFFFF)
    override val blue: Color
        get() = Color(0xFF588BD4)
    override val red: Color
        get() = Color(0xFFD45858)
    override val green: Color
        get() = Color(0xFF48CA72)
    override val secondary: Color
        get() = Color(0xFF6C6C6C)
    override val secondaryTwo: Color
        get() = Color(0xFFF9F9F9)
    override val primary: Color
        get() = Color(0xFF292929)
    override val basicUniform: Color
        get() = Color(0xFFFFFFFF)
    override val mask: Color
        get() = Color(0xFFCCCCCC)
    override val yellow: Color
        get() = Color(0xFFD9BA0D)
    override val blueLight: Color
        get() = Color(0xFF7EAFE1)
}

object LightColor : AppThemeColors {
    override val text: AppThemeColors.Text = LightText
    override val background: AppThemeColors.Background = LightBackground
}