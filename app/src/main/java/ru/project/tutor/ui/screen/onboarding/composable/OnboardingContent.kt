package ru.project.tutor.ui.screen.onboarding.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.onboarding.OnboardingStore

@Composable
private fun onboardingPages(): List<OnboardingStore.OnboardingPage> {
    val title1 = stringResource(R.string.onboarding_page_1_title)
    val desc1 = stringResource(R.string.onboarding_page_1_description)
    val title2 = stringResource(R.string.onboarding_page_2_title)
    val desc2 = stringResource(R.string.onboarding_page_2_description)
    val title3 = stringResource(R.string.onboarding_page_3_title)
    val desc3 = stringResource(R.string.onboarding_page_3_description)
    return listOf(
        OnboardingStore.OnboardingPage(
            emoji = "📚",
            title = title1,
            description = desc1,
        ),
        OnboardingStore.OnboardingPage(
            emoji = "🎯",
            title = title2,
            description = desc2,
        ),
        OnboardingStore.OnboardingPage(
            emoji = "🚀",
            title = title3,
            description = desc3,
        ),
    )
}

@Composable
fun OnboardingContent(
    uiState: OnboardingStore.UiState,
    onEvent: (OnboardingStore.Event) -> Unit,
) {
    val pages = onboardingPages()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    LaunchedEffect(uiState.currentPage) {
        if (pagerState.currentPage != uiState.currentPage) {
            pagerState.animateScrollToPage(uiState.currentPage)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != uiState.currentPage) {
            onEvent(OnboardingStore.Event.PageChanged(pagerState.currentPage))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background.basic)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.height(
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.onboarding_skip),
                    style = AppTheme.textStyle.subheadThree,
                    color = AppTheme.colors.text.placeholder.copy(
                        alpha = if (uiState.isLastPage) 0F else 1F
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .noRippleClickable { onEvent(OnboardingStore.Event.Skip) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingPageContent(pages[page])
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                pages.indices.forEach { index ->
                    PagerIndicator(
                        isActive = index == uiState.currentPage,
                        index = index
                    )
                    if (index < pages.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = if (uiState.isLastPage) stringResource(R.string.onboarding_start) else stringResource(
                    R.string.onboarding_next
                ),
                onClick = { onEvent(OnboardingStore.Event.NextPage) }
            )

            Spacer(modifier = Modifier.height(32.dp))
            BottomSpacerSystem()
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingStore.OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = page.emoji,
            style = AppTheme.textStyle.largeTitleOne,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = page.title,
            style = AppTheme.textStyle.titleOne,
            color = AppTheme.colors.text.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = page.description,
            style = AppTheme.textStyle.bodyTwo,
            color = AppTheme.colors.text.placeholder,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PagerIndicator(
    isActive: Boolean,
    index: Int,
) {
    val width by animateDpAsState(
        targetValue = if (isActive) 24.dp else 8.dp,
        label = "indicator_width"
    )

    val activeColor = AppTheme.colors.background.primary
    val inactiveColor = AppTheme.colors.background.secondary

    val color by animateColorAsState(
        targetValue = if (isActive) activeColor else inactiveColor,
        label = "indicator_color"
    )

    val scale by animateDpAsState(
        targetValue = if (isActive) 8.dp else 6.dp,
        label = "indicator_scale"
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(scale)
            .background(
                color = color,
                shape = CircleShape
            )
    )
}
