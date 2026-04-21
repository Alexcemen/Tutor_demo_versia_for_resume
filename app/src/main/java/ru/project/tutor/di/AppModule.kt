package ru.project.tutor.di

import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.project.tutor.BuildConfig
import ru.project.tutor.ads.AppBannerAdLoader
import ru.project.tutor.ads.AppInterstitialAdLoader
import ru.project.tutor.ads.AppTestResultBannerAdLoader
import ru.project.tutor.ads.BannerResultListener
import ru.project.tutor.ads.InterstitialResultListener
import ru.project.tutor.common_ui.composable.utils.StateKeeper
import ru.project.tutor.common_ui.composable.utils.StateKeeperImpl
import ru.project.tutor.common_ui.shared.AppReviewManager
import ru.project.tutor.common_ui.shared.GoogleReviewManager
import ru.project.tutor.common_ui.shared.RustoreReviewManager
import ru.project.tutor.domain.LocaleManager
import ru.project.tutor.notifications.NotificationHelper
import ru.project.tutor.notifications.NotificationScheduler
import ru.project.tutor.ui.screen.card_info.DebugTestFactory
import ru.project.tutor.utils.AdStarterSharedFlow
import ru.project.tutor.utils.AdStarterSharedFlowImpl
import ru.project.tutor.utils.AppCoroutineScope
import ru.project.tutor.utils.AppResource
import ru.project.tutor.utils.AppResourceImpl
import ru.project.tutor.utils.ClipboardWrapper
import ru.project.tutor.utils.IntentFileManagerRegister
import ru.project.tutor.utils.NetworkMonitor
import ru.project.tutor.utils.ReviewStarterSharedFlow
import ru.project.tutor.utils.ReviewStarterSharedFlowImpl

val appModule = module {
    single<StateKeeper> { StateKeeperImpl() }

    single<AppResource> { AppResourceImpl(androidContext()) }

    single<CoroutineScope> { AppCoroutineScope() }

    single<InterstitialResultListener> { InterstitialResultListener(get()) }

    single<AdStarterSharedFlow> { AdStarterSharedFlowImpl() }

    single<ReviewStarterSharedFlow> { ReviewStarterSharedFlowImpl() }

    single<AppInterstitialAdLoader> {
        AppInterstitialAdLoader(
            context = androidContext(),
            coroutineScope = get(),
            resultListener = get(),
            errorLogger = get()
        )
    }

    single<AppReviewManager> {
        if (BuildConfig.flavourName == "google") {
            GoogleReviewManager(androidContext())
        } else {
            RustoreReviewManager(androidContext())
        }
    }

    single<BannerResultListener> { BannerResultListener(get()) }

    single<AppBannerAdLoader> {
        AppBannerAdLoader(
            context = androidContext(),
            resultListener = get()
        )
    }

    single<AppTestResultBannerAdLoader> {
        AppTestResultBannerAdLoader(
            context = androidContext(),
            resultListener = get()
        )
    }

    single<NotificationHelper> { NotificationHelper(androidContext()) }

    single<NotificationScheduler> { NotificationScheduler(androidContext()) }

    single<NetworkMonitor> { NetworkMonitor(androidContext()) }

    single<IntentFileManagerRegister> { IntentFileManagerRegister() }

    single<ClipboardWrapper> { ClipboardWrapper(androidContext()) }

    single<DebugTestFactory> { DebugTestFactory(get(), get(), get()) }

    single<LocaleManager> { LocaleManager(get(), get()) }
}
