package ru.project.tutor.ui.screen.bottom_navigation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.common_ui.composable.elements.BottomNavigationContainer
import ru.project.tutor.common_ui.composable.elements.bottom_navigation.BottomNavigation
import ru.project.tutor.common_ui.composable.mvi.AppNavKey
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.navigation.CardInfo
import ru.project.tutor.ui.navigation.TestInfo
import ru.project.tutor.ui.navigation.provideBottom
import ru.project.tutor.ui.screen.bottom_navigation.BottomNavigationStore
import ru.project.tutor.ui.screen.bottom_navigation.BottomNavigationViewModel
import ru.project.tutor.ui.screen.card_info.CardInfoScreenContent
import ru.project.tutor.ui.screen.test_info.TestInfoScreenContent

val MainNavigation = compositionLocalOf<NavBackStack<AppNavKey>?> { null }

@Composable
@Preview
private fun BottomNavigationContentScreenPreview() {
    BottomNavigationContentScreen()
}

@Composable
fun BottomNavigationContentScreen() {
    val backStack = rememberNavBackStack(TestInfo()) as NavBackStack<AppNavKey>
    val viewModel = koinViewModel<BottomNavigationViewModel>()
    viewModel.sideEffect {
        when (it) {
            is BottomNavigationStore.SideEffect.NavigateHome -> {
                backStack.removeLastOrNull()
                backStack.add(TestInfo())
            }

            is BottomNavigationStore.SideEffect.NavigateProfile -> {
                backStack.removeLastOrNull()
                backStack.add(CardInfo())
            }
        }
    }
    val state by viewModel.uiState.collectAsState()
    BottomNavigationContainer(bottomBar = {
        BottomNavigation(state.listNavigation) {
            viewModel.onEvent(BottomNavigationStore.Event.SelectItem(it))
        }
    }) {
        NavDisplay(
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<TestInfo> {
                    backStack.provideBottom(TestInfo::class) { TestInfoScreenContent() }
                }

                entry<CardInfo> {
                    backStack.provideBottom(CardInfo::class) { CardInfoScreenContent() }
                }
            }
        )
    }
}