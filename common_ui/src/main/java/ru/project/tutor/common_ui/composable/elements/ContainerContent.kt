package ru.project.tutor.common_ui.composable.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Composable
fun BottomNavigationContainer(
    background: Color = AppTheme.colors.background.basic,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Box(Modifier.weight(1f)) {
            content()
        }
        bottomBar()
    }
}

@Composable
fun ContainerContent(
    background: Color = AppTheme.colors.background.basic,
    applyHorizontalPadding: Boolean = true,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val modifier = Modifier
    val currentModifier = if (applyHorizontalPadding) {
        modifier.padding(horizontal = 16.dp)
    } else modifier
    Column(
        Modifier
            .fillMaxSize()
            .background(background)
    ) {
        //Spacer(Modifier.height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
        topBar()
        Column(currentModifier.weight(1f)) { content() }
        bottomBar()
    }
}

@Composable
fun TopBarContainer(
    topBar: @Composable () -> Unit = {},
) {
    Column {
        topBar()
    }
}

@Composable
fun BottomSpacerSystem() {
    Spacer(
        Modifier.height(
            WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        )
    )
}

@Composable
fun TopBarSpacer() {
    Spacer(Modifier.height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
}

@Composable
fun SpacerHeight(height: Dp) {
    Spacer(Modifier.height(height))
}

@Composable
fun SpacerWidth(width: Dp) {
    Spacer(Modifier.width(width))
}