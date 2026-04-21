package ru.project.tutor.ui.screen.bottom_navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.bottom_navigation.BottomNavigationItem
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.utils.AppResource

class BottomNavigationViewModel(
    reducer: BottomNavigationReducer,
    private val appResource: AppResource,
) : ScreenViewModel<BottomNavigationStore.State, BottomNavigationStore.Event, BottomNavigationStore.SideEffect, BottomNavigationStore.Effect, BottomNavigationStore.UiState>(
    reducer
) {

    init {
        forceEffect(
            BottomNavigationStore.Effect.UpdateSelected(
                createList(1)
            )
        )
    }

    override fun createState(): BottomNavigationStore.State = BottomNavigationStore.State(
        emptyList()
    )

    override fun handleEffect(
        currentState: BottomNavigationStore.State,
        effect: BottomNavigationStore.Effect,
    ): BottomNavigationStore.State {
        return if (effect is BottomNavigationStore.Effect.UpdateSelected) {
            currentState.copy(listNavigation = effect.newList)
        } else currentState
    }

    override fun handleEvent(
        currentState: BottomNavigationStore.State,
        intent: BottomNavigationStore.Event,
    ): Flow<BottomNavigationStore.Effect> {
        return when (intent) {
            is BottomNavigationStore.Event.SelectItem -> flow {
                if (intent.position == 1) {
                    sendSideEffect(BottomNavigationStore.SideEffect.NavigateHome)
                } else {
                    sendSideEffect(BottomNavigationStore.SideEffect.NavigateProfile)
                }
                emit(
                    BottomNavigationStore.Effect.UpdateSelected(
                        createList(
                            selectedPosition = intent.position
                        )
                    )
                )
            }
        }
    }

    private fun createList(selectedPosition: Int): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                position = 1,
                text = appResource.getString(R.string.bottom_title_home),
                image = R.drawable.ic_home,
                isSelected = selectedPosition == 1,
            ),
            BottomNavigationItem(
                position = 2,
                text = appResource.getString(R.string.bottom_title_profile),
                image = R.drawable.ic_person,
                isSelected = selectedPosition == 2,
            ),
        )
    }

}