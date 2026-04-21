package ru.project.tutor.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.project.tutor.BuildConfig
import ru.project.tutor.R
import ru.project.tutor.ads.AppInterstitialAdLoader
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.AppNavKey
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.StateKeeper
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.common_ui.shared.AppReviewManager
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.notifications.NotificationHelper
import ru.project.tutor.notifications.NotificationScheduler
import ru.project.tutor.ui.navigation.AdExplanation
import ru.project.tutor.ui.navigation.BottomNavigation
import ru.project.tutor.ui.navigation.ManualAi
import ru.project.tutor.ui.navigation.Onboarding
import ru.project.tutor.ui.navigation.QuestionFactory
import ru.project.tutor.ui.navigation.QuestionsList
import ru.project.tutor.ui.navigation.SplashScreen
import ru.project.tutor.ui.navigation.StartingTesting
import ru.project.tutor.ui.navigation.TestFactory
import ru.project.tutor.ui.navigation.TestManager
import ru.project.tutor.ui.navigation.TestProcess
import ru.project.tutor.ui.navigation.TestResult
import ru.project.tutor.ui.navigation.TestsList
import ru.project.tutor.ui.navigation.provide
import ru.project.tutor.ui.screen.ad_explanation.AdExplanationScreenContent
import ru.project.tutor.ui.screen.bottom_navigation.BottomNavigationScreenContent
import ru.project.tutor.ui.screen.loading.SplashScreenContent
import ru.project.tutor.ui.screen.manual_ai.ManualAiScreen
import ru.project.tutor.ui.screen.onboarding.OnboardingScreen
import ru.project.tutor.ui.screen.question_factory.QuestionFactoryScreenContent
import ru.project.tutor.ui.screen.questions_list.QuestionsListScreenContent
import ru.project.tutor.ui.screen.starting_testing.StartingTestingScreenContent
import ru.project.tutor.ui.screen.test_factory.TestFactoryScreenContent
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_manager.TestManagerScreenContent
import ru.project.tutor.ui.screen.test_process.TestProcessScreenContent
import ru.project.tutor.ui.screen.test_result.TestResultScreenContent
import ru.project.tutor.ui.screen.tests_list.tests_list.TestsListScreenContent
import ru.project.tutor.utils.AdStarterSharedFlow
import ru.project.tutor.utils.IntentFileManagerRegister
import ru.project.tutor.utils.NetworkMonitor
import ru.project.tutor.utils.ReviewStarterSharedFlow
import ru.project.tutor.utils.showToast
import timber.log.Timber

val RootNavigation = compositionLocalOf<NavBackStack<AppNavKey>?> { null }

class MainActivity : ComponentActivity(), KoinComponent {

    private val stateKeeper: StateKeeper by inject()
    private val appInterstitialAdLoader: AppInterstitialAdLoader by inject()
    private val adStarterSharedFlow: AdStarterSharedFlow by inject()
    private val reviewStarterSharedFlow: ReviewStarterSharedFlow by inject()
    private val appReviewManager: AppReviewManager by inject()
    private val intentFileManagerRegister: IntentFileManagerRegister by inject()
    private val notificationScheduler: NotificationScheduler by inject()
    private val appSharedPreferences: AppSharedPreferences by inject()
    private val networkMonitor: NetworkMonitor by inject()

    private val viewModel: MainActivityViewModel by lazy { getKoin().get() }

    private val log = Timber.tag(MainActivity::class.java.name)

    private var pendingNotificationDestination by mutableStateOf<String?>(null)

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            log.i("POST_NOTIFICATIONS permission granted: $isGranted")
        }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        log.i("onRestoreInstanceState")
        stateKeeper.update(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        log.i("onSaveInstanceState")
        outState.putAll(stateKeeper.getAll())
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (appSharedPreferences.notificationsEnabled) {
            notificationScheduler.schedule()
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        if (savedInstanceState == null) {
            handleIntent(intent)
        }
        intentFileManagerRegister.init(this@MainActivity)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.DebugTree())
        }
        log.i("onCreate, activity = $this")
        if (!BuildConfig.DEBUG) {
            appReviewManager.request()
        }
        lifecycleScope.launch {
            adStarterSharedFlow.subscriber.collect {
                appInterstitialAdLoader.tryShow(this@MainActivity)
            }
        }
        lifecycleScope.launch {
            reviewStarterSharedFlow.subscriber.collect {
                appReviewManager.start(this@MainActivity)
            }
        }
        setContent {
            AppTheme {
                val backStack = rememberNavBackStack(SplashScreen()) as NavBackStack<AppNavKey>
                LaunchedEffect(Unit) {
                    viewModel.sideEffectTestId.collect { testId ->
                        backStack.add(TestManager(testId = testId))
                    }
                }
                LaunchedEffect(pendingNotificationDestination) {
                    pendingNotificationDestination?.let { dest ->
                        pendingNotificationDestination = null
                        backStack.add(BottomNavigation())
                        when (dest) {
                            "TESTS_NORMAL" -> backStack.add(TestsList(testsListType = TestsListType.NORMAL))
                            "TESTS_ERRORS" -> backStack.add(TestsList(testsListType = TestsListType.ERRORS))
                            "TEST_FACTORY" -> backStack.add(TestFactory())
                        }
                    }
                }

                val showNoInternetOverlay = rememberNoInternetOverlay(networkMonitor)
                if (!showNoInternetOverlay) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        NavDisplay(
                            backStack = backStack,
                            onBack = {
                                if (backStack.last() is TestResult) {
                                    backStack.removeIf { it.type != BottomNavigation::class.simpleName }
                                } else {
                                    backStack.removeLastOrNull()
                                }
                            },
                            entryDecorators = listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator()
                            ),
                            entryProvider = entryProvider {
                                entry<SplashScreen> {
                                    backStack.provide(SplashScreen::class, it) {
                                        SplashScreenContent()
                                    }
                                }

                                entry<TestFactory> {
                                    backStack.provide(TestFactory::class, it) {
                                        TestFactoryScreenContent()
                                    }
                                }

                                entry<BottomNavigation> {
                                    backStack.provide(BottomNavigation::class, it) {
                                        BottomNavigationScreenContent()
                                    }
                                }

                                entry<TestManager> {
                                    backStack.provide(TestManager::class, it) {
                                        TestManagerScreenContent(testId = it.testId)
                                    }
                                }

                                entry<QuestionFactory> {
                                    backStack.provide(QuestionFactory::class, it) {
                                        QuestionFactoryScreenContent(
                                            testId = it.testId,
                                            questionId = it.questionId
                                        )
                                    }
                                }

                                entry<StartingTesting> {
                                    backStack.provide(StartingTesting::class, it) {
                                        StartingTestingScreenContent(testId = it.testId, it.mode)
                                    }
                                }

                                entry<TestProcess> {
                                    backStack.provide(TestProcess::class, it) {
                                        TestProcessScreenContent(
                                            testMode = it.testMode,
                                            startingMode = it.startingMode,
                                            testId = it.testId,
                                            options = it.options
                                        )
                                    }
                                }

                                entry<TestResult> {
                                    backStack.provide(TestResult::class, it) {
                                        TestResultScreenContent(it.testId, it.attemptId)
                                    }
                                }

                                entry<TestsList> {
                                    backStack.provide(TestsList::class, it) {
                                        TestsListScreenContent(it.testsListType)
                                    }
                                }

                                entry<QuestionsList> {
                                    backStack.provide(QuestionsList::class, it) {
                                        QuestionsListScreenContent(it.testId, it.testsListType)
                                    }
                                }

                                entry<ManualAi> {
                                    backStack.provide(ManualAi::class, it) {
                                        ManualAiScreen()
                                    }
                                }

                                entry<Onboarding> {
                                    backStack.provide(Onboarding::class, it) {
                                        OnboardingScreen()
                                    }
                                }

                                entry<AdExplanation> {
                                    backStack.provide(AdExplanation::class, it) {
                                        AdExplanationScreenContent()
                                    }
                                }
                            }
                        )
                    }
                } else {
                    NoInternetOverlay()
                }
            }
        }
        if (savedInstanceState == null) {
            AppAnalytics.openAppFlavour()
        }
    }

    override fun onResume() {
        super.onResume()
        if (appSharedPreferences.notificationsEnabled) {
            notificationScheduler.schedule()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        when {
            intent.action == Intent.ACTION_VIEW -> handleViewIntent(intent)
            intent.hasExtra(NotificationHelper.EXTRA_DESTINATION) -> {
                pendingNotificationDestination =
                    intent.getStringExtra(NotificationHelper.EXTRA_DESTINATION)
            }
        }
    }

    private fun handleViewIntent(intent: Intent) {
        val uri = intent.data ?: run {
            showToast(R.string.handle_file_json_error_not_found)
            return
        }

        val mimeType = contentResolver.getType(uri)
        if (mimeType?.contains("json") != true) {
            showToast(R.string.handle_file_json_error_file_format)
            return
        }

        viewModel.processSaveSharedTest(uri)
    }

    override fun onDestroy() {
        log.i("onDestroy")
        super.onDestroy()
    }
}

@Composable
private fun rememberNoInternetOverlay(networkMonitor: NetworkMonitor): Boolean {
    val isOnline by networkMonitor.isOnline.collectAsState(initial = true)
    val showOverlay = !isOnline

    LaunchedEffect(showOverlay) {
        if (showOverlay) {
            AppAnalytics.noInternet()
        }
    }

    return showOverlay
}

@Composable
private fun NoInternetOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background.basic)
            .noRippleClickable(
                enabled = true,
                onClick = {},
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp),
        ) {
            Text(
                text = "📡",
                style = AppTheme.textStyle.largeTitleOne,
                modifier = Modifier.padding(bottom = 24.dp),
            )
            Text(
                text = stringResource(R.string.no_internet_title),
                style = AppTheme.textStyle.titleOne,
                color = AppTheme.colors.text.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            Text(
                text = stringResource(R.string.no_internet_description),
                style = AppTheme.textStyle.bodyTwo,
                color = AppTheme.colors.text.placeholder,
                textAlign = TextAlign.Center,
            )
        }
    }
}
