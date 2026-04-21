package ru.project.tutor.ui.screen.test_process.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import ru.project.tutor.ads.AppBannerAdLoader

@Composable
fun YandexBannerAd(
    adLoader: AppBannerAdLoader,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = {
            adLoader.bannerAdView
        },
        modifier = modifier,
    )
}
