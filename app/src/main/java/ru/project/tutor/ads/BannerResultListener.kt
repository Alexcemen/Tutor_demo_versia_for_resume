package ru.project.tutor.ads

import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class BannerResultListener(private val coroutineScope: CoroutineScope) : BannerAdEventListener {

    val onBannerLoadedFlow = MutableSharedFlow<Unit>(replay = 0)
    val onBannerFailedFlow = MutableSharedFlow<AdRequestError>(replay = 0)
    val onBannerClickedFlow = MutableSharedFlow<Unit>(replay = 0)

    override fun onAdLoaded() {
        coroutineScope.launch { onBannerLoadedFlow.emit(Unit) }
    }

    override fun onAdFailedToLoad(error: AdRequestError) {
        coroutineScope.launch { onBannerFailedFlow.emit(error) }
    }

    override fun onAdClicked() {
        coroutineScope.launch { onBannerClickedFlow.emit(Unit) }
    }

    override fun onLeftApplication() {}

    override fun onReturnedToApplication() {}

    override fun onImpression(impressionData: ImpressionData?) {}
}
