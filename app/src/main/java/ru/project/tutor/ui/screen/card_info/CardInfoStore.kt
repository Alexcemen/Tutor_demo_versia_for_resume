package ru.project.tutor.ui.screen.card_info

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.domain.models.Language
import ru.project.tutor.domain.models.card_info.CardInfoStatistics
import ru.project.tutor.ui.screen.card_info.models.CardInfoStatisticsUi

object CardInfoStore {
    data class State(
        val shortStatistics: CardInfoStatistics = CardInfoStatistics(),
        val notificationsEnabled: Boolean = true,
        val settingsWithNotificationOpen: Boolean = false,
        val selectedLanguage: Language = Language(Language.CODE_SYSTEM),
    ) : MviState

    data class UiState(
        val shortStatistics: CardInfoStatisticsUi,
        val notificationsEnabled: Boolean,
        val settingsWithNotificationOpen: Boolean,
        val selectedLanguage: Language,
        val availableLanguages: List<Language>,
        val showLanguageSetting: Boolean,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data class OpenLink(val url: String) : SideEffect
        data object OpenAdExplanation : SideEffect
        data object OpenReview : SideEffect
    }

    sealed interface Effect : MviEffect {
        data class SetStatistics(val cardInfoStatistics: CardInfoStatistics) : Effect
        data class SetNotificationsEnabled(val enabled: Boolean) : Effect
        data class SetSelectedLanguage(val language: Language) : Effect
        data object ToggleSettingsWithNotification : Effect
    }

    sealed interface Event : MviEvent {
        data object LoadScreen : Event
        data object OpenPrivacyPolicy : Event
        data object OpenSupportChat : Event
        data object OpenAdExplanation : Event
        data object OpenReview : Event
        data object CreateTestForBuildDebug : Event
        data class ToggleNotifications(val enabled: Boolean) : Event
        data object ToggleSettingsWithNotification : Event
        data class SelectLanguage(val language: Language) : Event
    }
}