package ru.project.tutor.ui.screen.ad_explanation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.utils.UrlConst

class AdExplanationViewModel(
    reducer: AdExplanationReducer,
) : ScreenViewModel<AdExplanationStore.State, AdExplanationStore.Event, AdExplanationStore.SideEffect, AdExplanationStore.Effect, AdExplanationStore.UiState>(
    reducer
) {

    init {
        forceEffect(AdExplanationStore.Effect.InitTelegramLink(UrlConst.TELEGRAM))
    }

    override fun createState(): AdExplanationStore.State = AdExplanationStore.State()

    override fun handleEffect(
        currentState: AdExplanationStore.State,
        effect: AdExplanationStore.Effect,
    ): AdExplanationStore.State {
        return when (effect) {
            is AdExplanationStore.Effect.InitTelegramLink -> {
                currentState.copy(telegramLink = effect.link)
            }
        }
    }

    override fun handleEvent(
        currentState: AdExplanationStore.State,
        intent: AdExplanationStore.Event,
    ): Flow<AdExplanationStore.Effect> = when (intent) {
        AdExplanationStore.Event.Close -> flow {
            sendSideEffect(AdExplanationStore.SideEffect.Close)
        }

        AdExplanationStore.Event.OpenTelegram -> flow {
            sendSideEffect(AdExplanationStore.SideEffect.OpenTelegram)
        }
    }
}
