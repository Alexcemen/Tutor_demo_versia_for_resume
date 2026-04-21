package ru.project.tutor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation3.runtime.NavBackStack
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.AppNavKey
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.screen.bottom_navigation.composable.MainNavigation
import timber.log.Timber
import kotlin.reflect.KClass

@Composable
fun NavBackStack<AppNavKey>.provide(
    name: KClass<*>,
    item: AppNavKey,
    content: @Composable () -> Unit,
) {
    Timber.tag("Navigator").i("start screen ${item}")
    AppAnalytics.trackScreenEvent(name.simpleName.toString())
    CompositionLocalProvider(RootNavigation provides this) {
        content()
    }
}

@Composable
fun NavBackStack<AppNavKey>.provideBottom(name: KClass<*>, content: @Composable () -> Unit) {
    Timber.tag("Navigator").i("start screen ${name.simpleName}")
    CompositionLocalProvider(MainNavigation provides this) {
        content()
    }
}