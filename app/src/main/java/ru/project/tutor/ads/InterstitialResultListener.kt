package ru.project.tutor.ads

import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class InterstitialResultListener(private val coroutineScope: CoroutineScope) :
    InterstitialAdEventListener {

    val onAdDismissedFlow = MutableSharedFlow<Boolean>()
    val onAdFailedToShowFlow = MutableSharedFlow<AdError>()

    override fun onAdClicked() {

    }

    override fun onAdDismissed() {
        coroutineScope.launch { onAdDismissedFlow.emit(true) }
    }

    fun onAdDismissed(withReload: Boolean) {
        coroutineScope.launch { onAdDismissedFlow.emit(withReload) }
    }

    override fun onAdFailedToShow(adError: AdError) {
        coroutineScope.launch {
            onAdFailedToShowFlow.emit(adError)
            onAdDismissedFlow.emit(true)
        }
    }

    override fun onAdImpression(impressionData: ImpressionData?) {

    }

    override fun onAdShown() {

    }
}