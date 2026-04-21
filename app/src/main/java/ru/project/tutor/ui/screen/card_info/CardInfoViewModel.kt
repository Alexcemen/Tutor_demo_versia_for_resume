package ru.project.tutor.ui.screen.card_info

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.LocaleManager
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.notifications.NotificationScheduler
import ru.project.tutor.utils.UrlConst
import ru.project.tutor.utils.withIO

class CardInfoViewModel(
    reducer: CardInfoReducer,
    private val appSharedPreferences: AppSharedPreferences,
    private val notificationScheduler: NotificationScheduler,
    private val debugTestFactory: DebugTestFactory,
    private val localeManager: LocaleManager,
    private val context: Context,
) : ScreenViewModel<
        CardInfoStore.State,
        CardInfoStore.Event,
        CardInfoStore.SideEffect,
        CardInfoStore.Effect,
        CardInfoStore.UiState
        >(
    reducer
) {
    init {
        onEvent(CardInfoStore.Event.LoadScreen)
    }

    override fun createState(): CardInfoStore.State = CardInfoStore.State()

    override fun handleEvent(
        currentState: CardInfoStore.State,
        intent: CardInfoStore.Event,
    ): Flow<CardInfoStore.Effect> = when (intent) {
        CardInfoStore.Event.LoadScreen -> flow {
            emit(CardInfoStore.Effect.SetNotificationsEnabled(enabled = appSharedPreferences.notificationsEnabled))
            val savedLanguage = localeManager.getSavedLanguage()
            emit(CardInfoStore.Effect.SetSelectedLanguage(savedLanguage))
        }

        is CardInfoStore.Event.OpenPrivacyPolicy -> flow {
            sendSideEffect(
                CardInfoStore.SideEffect.OpenLink(
                    url = UrlConst.POLICY
                )
            )
        }

        is CardInfoStore.Event.OpenSupportChat -> flow {
            sendSideEffect(
                CardInfoStore.SideEffect.OpenLink(
                    url = UrlConst.TELEGRAM
                )
            )
        }

        is CardInfoStore.Event.OpenAdExplanation -> flow {
            AppAnalytics.openAdExplanation()
            sendSideEffect(CardInfoStore.SideEffect.OpenAdExplanation)
        }

        is CardInfoStore.Event.OpenReview -> flow {
            AppAnalytics.openReview()
            sendSideEffect(CardInfoStore.SideEffect.OpenReview)
        }

        is CardInfoStore.Event.ToggleNotifications -> flow {
            withIO {
                appSharedPreferences.notificationsEnabled = intent.enabled
                if (intent.enabled) {
                    notificationScheduler.schedule()
                } else {
                    notificationScheduler.cancel()
                }
            }
            emit(CardInfoStore.Effect.SetNotificationsEnabled(enabled = intent.enabled))
        }

        is CardInfoStore.Event.ToggleSettingsWithNotification -> flow {
            emit(CardInfoStore.Effect.ToggleSettingsWithNotification)
        }

        is CardInfoStore.Event.CreateTestForBuildDebug -> flow {
            debugTestFactory.createDebugTest()
        }

        is CardInfoStore.Event.SelectLanguage -> flow {
            withIO {
                localeManager.setLanguage(intent.language.code, context)
                AppAnalytics.selectLanguage(intent.language.code)
            }
            emit(CardInfoStore.Effect.SetSelectedLanguage(intent.language))
        }
    }

    override fun handleEffect(
        currentState: CardInfoStore.State,
        effect: CardInfoStore.Effect,
    ): CardInfoStore.State {
        return when (effect) {
            is CardInfoStore.Effect.SetStatistics -> {
                currentState.copy(
                    shortStatistics = effect.cardInfoStatistics
                )
            }

            is CardInfoStore.Effect.SetNotificationsEnabled -> {
                currentState.copy(notificationsEnabled = effect.enabled)
            }

            is CardInfoStore.Effect.ToggleSettingsWithNotification -> {
                currentState.copy(settingsWithNotificationOpen = !currentState.settingsWithNotificationOpen)
            }

            is CardInfoStore.Effect.SetSelectedLanguage -> {
                currentState.copy(selectedLanguage = effect.language)
            }
        }
    }

}