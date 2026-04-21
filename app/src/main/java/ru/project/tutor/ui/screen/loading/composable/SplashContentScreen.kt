package ru.project.tutor.ui.screen.loading.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.loading.SplashScreenStore

@Composable
@Preview
private fun SplashContentScreenPreview() {
    SplashContentScreen() {}
}

@Composable
fun SplashContentScreen(onEvent: (SplashScreenStore.Event) -> Unit) {
    var animationState by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        delay(500)
        animationState = 1
        delay(300)
        animationState = 2
        delay(300)
        animationState = 3
        delay(900)
        onEvent(SplashScreenStore.Event.AnimationFinish)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background.basic),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(if (isSystemInDarkTheme()) R.drawable.ic_app_icon_dark else R.drawable.ic_app_icon),
            contentDescription = "",
            modifier = Modifier
                .size(77.dp)
                .offset(
                    y = animateDpAsState(
                        targetValue = when (animationState) {
                            0 -> 0.dp
                            else -> (-72).dp
                        },
                        animationSpec = tween(durationMillis = 500)
                    ).value
                )
        )
        AnimatedVisibility(
            visible = animationState >= 2,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                    slideInVertically(animationSpec = tween(durationMillis = 500)) { it / -2 },
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = AppTheme.textStyle.largeTitleOne,
                color = AppTheme.colors.text.primary,
            )
        }
        AnimatedVisibility(
            visible = animationState >= 3,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInVertically(
                animationSpec = tween(durationMillis = 500)
            ) { it / -4 },
        ) {
            Text(
                text = stringResource(R.string.welcome),
                style = AppTheme.textStyle.titleTwo,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 72.dp)
            )
        }
    }
}