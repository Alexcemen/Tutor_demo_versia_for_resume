package ru.project.tutor.ads

import android.content.Context
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import ru.project.tutor.BuildConfig

class AppTestResultBannerAdLoader(
    context: Context,
    private val resultListener: BannerResultListener,
) {
    val bannerAdView: BannerAdView = BannerAdView(context).apply {
        setAdUnitId(BuildConfig.bannerTestResultUnitId)
        setAdSize(getAdSize(context))
        setBannerAdEventListener(resultListener)
    }

    private fun getAdSize(context: Context): BannerAdSize {
        val widthPx = context.resources.displayMetrics.widthPixels
        val widthDp = (widthPx / context.resources.displayMetrics.density).toInt()
        return BannerAdSize.inlineSize(context, widthDp, 80)
    }

    fun reloadBanner() {
        bannerAdView.loadAd(AdRequest.Builder().build())
    }
}
