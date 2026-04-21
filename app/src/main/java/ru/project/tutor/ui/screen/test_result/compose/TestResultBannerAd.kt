package ru.project.tutor.ui.screen.test_result.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import ru.project.tutor.ads.AppTestResultBannerAdLoader

@Composable
fun TestResultBannerAd(
    adLoader: AppTestResultBannerAdLoader,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = {
            adLoader.bannerAdView
        },
        modifier = modifier,
    )
}
