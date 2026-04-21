package ru.project.tutor.ads

import android.content.Context
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.project.tutor.BuildConfig
import ru.project.tutor.domain.repository.ErrorLogger
import ru.project.tutor.ui.MainActivity
import ru.project.tutor.utils.AppErrorLogger
import timber.log.Timber

class AppInterstitialAdLoader(
    context: Context,
    coroutineScope: CoroutineScope,
    private val resultListener: InterstitialResultListener,
    private val errorLogger: ErrorLogger,
) {

    companion object {
        private const val ONE_MILLIS_MINUTES = 60_000F
        private const val DEMO_KEY = "demo-interstitial-yandex"
    }

    private var interstitialAdLoader: InterstitialAdLoader? = null
    private var interstitialAd: InterstitialAd? = null
    private var lastShowMillis: Long = System.currentTimeMillis()
    private val log = Timber.tag(AppInterstitialAdLoader::class.java.name)
    private val isDebugBuild: Boolean = BuildConfig.DEBUG

    init {
        interstitialAdLoader = InterstitialAdLoader(context).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    log.i("Success load ad")
                    this@AppInterstitialAdLoader.interstitialAd = interstitialAd
                    this@AppInterstitialAdLoader.interstitialAd?.setAdEventListener(resultListener)
                }

                override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                    AppErrorLogger.logMessage(
                        "Failed to onAdFailedToLoad, " +
                                "message = ${adRequestError.description}, " +
                                "code = ${adRequestError.code}"
                    )
                }
            })
        }
        loadInterstitialAd()
        coroutineScope.launch {
            resultListener.onAdFailedToShowFlow.collect {
                AppErrorLogger.logMessage("Failed to show ad, description = ${it.description}")
                destroyInterstitialAd()
                loadInterstitialAd()
            }
        }
        coroutineScope.launch {
            resultListener.onAdDismissedFlow.collect {
                if (!it) return@collect
                log.i("catch event:: onAdDismissedFlow")
                destroyInterstitialAd()
                loadInterstitialAd()
            }
        }
    }

    fun tryShow(activity: MainActivity) {
        if (isDebugBuild) {
            log.i("Ads can not show because build debug")
            resultListener.onAdDismissed(true)
            return
        }
        log.i("try show ads")
        if (interstitialAd == null) {
            errorLogger.logMessage("interstitialAd = null")
            resultListener.onAdDismissed(true)
            return
        }
        val currentTime = System.currentTimeMillis()
        val difference = (currentTime - lastShowMillis) / ONE_MILLIS_MINUTES
        if (difference <= 3) {
            log.i("It hasn't been 3 minutes for the ad to be displayed yet, difference = $difference")
            resultListener.onAdDismissed(false)
            return
        }
        log.i("success show ads, difference minutes = $difference")
        lastShowMillis = currentTime
        interstitialAd?.show(activity)
    }

    private fun loadInterstitialAd() {
        if (isDebugBuild) {
            log.i("Ads can not load because build debug")
            return
        }
        val adRequestConfiguration =
            AdRequestConfiguration.Builder(BuildConfig.adUnitId).build()
        log.i("Start load interstitial ad, adUnitId is debug = ${BuildConfig.adUnitId == DEMO_KEY}")
        interstitialAdLoader?.loadAd(adRequestConfiguration)
    }

    private fun destroyInterstitialAd() {
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }

    fun clearAll() {
        interstitialAdLoader?.setAdLoadListener(null)
        interstitialAdLoader = null
        destroyInterstitialAd()
    }
}
