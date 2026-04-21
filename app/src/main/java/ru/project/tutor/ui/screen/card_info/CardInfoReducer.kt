package ru.project.tutor.ui.screen.card_info

import ru.project.tutor.common_ui.composable.mvi.Reducer
import ru.project.tutor.domain.LocaleManager
import ru.project.tutor.domain.models.Language
import ru.project.tutor.domain.models.card_info.CardInfoStatistics
import ru.project.tutor.ui.screen.card_info.models.CardInfoStatisticsUi

class CardInfoReducer(
    private val localeManager: LocaleManager,
) : Reducer<CardInfoStore.State, CardInfoStore.UiState> {
    override fun reduce(state: CardInfoStore.State): CardInfoStore.UiState {
        return CardInfoStore.UiState(
            shortStatistics = state.shortStatistics.convertToUi(),
            notificationsEnabled = state.notificationsEnabled,
            settingsWithNotificationOpen = state.settingsWithNotificationOpen,
            selectedLanguage = state.selectedLanguage,
            availableLanguages = Language.supportedLanguages,
            showLanguageSetting = localeManager.isLanguageSelectionSupported()
        )
    }

    private fun CardInfoStatistics.convertToUi() = CardInfoStatisticsUi(
        countCardBlocks = countCardBlocks,
        timeSpendSeconds = timeSpendSeconds,
        countTrue = countTrue,
        countFalse = countFalse
    )
}