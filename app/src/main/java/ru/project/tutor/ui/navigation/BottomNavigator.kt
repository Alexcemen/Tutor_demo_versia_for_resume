package ru.project.tutor.ui.navigation

import kotlinx.serialization.Serializable
import ru.project.tutor.common_ui.composable.mvi.AppNavKey

@Serializable
data class TestInfo(override val type: String = TestInfo::class.simpleName.toString()) : AppNavKey()

@Serializable
data class CardInfo(override val type: String = CardInfo::class.simpleName.toString()) : AppNavKey()