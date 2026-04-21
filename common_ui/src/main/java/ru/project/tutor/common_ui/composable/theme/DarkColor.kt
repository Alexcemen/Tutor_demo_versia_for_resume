package ru.project.tutor.common_ui.composable.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
object DarkText : AppThemeColors.Text {
    override val primary: Color
        get() = Color(0xFFFFFFFF)
    override val placeholder: Color
        get() = Color(0xFFA6A6A6)
    override val red: Color
        get() = Color(0xFFE67474)
    override val blue: Color
        get() = Color(0xFF588BD4)
    override val primaryUniform: Color
        get() = Color(0xFF1C1C1C)
    override val reverse: Color
        get() = Color(0xFF000000)
    override val blueTwo: Color
        get() = Color(0xFF4987C5)
    override val whiteUniform: Color
        get() = Color(0xFFFFFFFF)
    override val reversed: Color
        get() = Color(0xFFFFFFFF)
}

@Immutable
object DarkBackground : AppThemeColors.Background {
    override val basic: Color
        get() = Color(0xFF262626)
    override val blue: Color
        get() = Color(0xFF376EB6)
    override val red: Color
        get() = Color(0xFF9A3B3B)
    override val green: Color
        get() = Color(0xFF33914D)
    override val secondary: Color
        get() = Color(0xFF9C9C9C)
    override val secondaryTwo: Color
        get() = Color(0xFF3F3F3F)
    override val primary: Color
        get() = Color(0xFFF6F6F6)
    override val basicUniform: Color
        get() = Color(0xFFFFFFFF)
    override val mask: Color
        get() = Color(0xFF8F8F8F)
    override val yellow: Color
        get() = Color(0xFFA68E0F)
    override val blueLight: Color
        get() = Color(0xFF3C6479)
}

object DarkColor : AppThemeColors {
    override val text: AppThemeColors.Text = DarkText
    override val background: AppThemeColors.Background = DarkBackground
}