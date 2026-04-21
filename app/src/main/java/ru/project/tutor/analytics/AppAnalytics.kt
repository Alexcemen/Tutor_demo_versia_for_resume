package ru.project.tutor.analytics

import android.os.Bundle
import androidx.core.os.bundleOf
import timber.log.Timber

object AppAnalytics {
    /**
     * статистика пользователя
     * fist_launch_date - дата первого запуска
     * all_count_test_created - количество созданных тестов
     * all_count_errors - количество допущенных ошибок
     * all_count_test_completed - количество тестов завершено
     * time_pass_test - общее время тестирования
     */
    fun userStatistics(
        firstLaunchDate: String,
        allCountTestCreated: Long,
        allCountErrors: Long,
        allCountTestsCompleted: Long,
        allCountTime: Long,
    ) {
        logEvent(
            "user_statistics", bundleOf(
                "fist_launch_date" to firstLaunchDate,
                "all_count_test_created" to allCountTestCreated,
                "all_count_errors" to allCountErrors,
                "all_count_test_completed" to allCountTestsCompleted,
                "time_pass_test" to allCountTime
            )
        )
    }

    //создание нового теста
    fun testCreated(nameTest: String) {
        logEvent(
            "create_new_test", bundleOf(
                "name" to nameTest
            )
        )
    }

    //создание нового теста через file
    fun testCreatedFile() {
        logEvent(
            "create_new_test_file"
        )
    }

    //нажали на инструкцию ai
    fun clickManual() {
        logEvent(
            "create_new_test_click_manual"
        )
    }

    //удаление теста
    fun deleteTest() {
        logEvent(
            "delete_test", bundleOf()
        )
    }

    //удаление вопроса
    fun deleteQuestion() {
        logEvent(
            "delete_question", bundleOf()
        )
    }

    //создание нового теста
    fun openCreateNewTest() {
        logEvent(
            "click_create_new_test", bundleOf()
        )
    }

    //создание нового теста
    fun openCreateNewTestEmptyBlock() {
        logEvent(
            "click_create_new_test_empty_block", bundleOf()
        )
    }

    //открыть экран списка тестов
    fun clickTestList(type: String) {
        logEvent(
            "click_test_list_${type}", bundleOf()
        )
    }

    //выбор режима тестирования
    fun switchModeTesting(isExam: Boolean) {
        val text = if (isExam) {
            "exam"
        } else {
            "workout"
        }
        logEvent(
            "click_test_list_${text}", bundleOf()
        )
    }

    //Установлен таймер в тренировке
    fun setToggleDuration(timerEnabled: Boolean) {
        if (timerEnabled) return
        logEvent("start_testing_set_duration", bundleOf())
    }

    //Перемешать вопросы
    fun setToggleShuffleQuestion(shuffle: Boolean) {
        if (shuffle) return
        logEvent("start_testing_set_shuffle_question", bundleOf())
    }

    //перемешать ответы
    fun setToggleShuffleAnswer(shuffle: Boolean) {
        if (shuffle) return
        logEvent("start_testing_set_shuffle_answer", bundleOf())
    }

    //показать правильные ответы
    fun setToggleShowAnswer(show: Boolean) {
        if (show) return
        logEvent("start_testing_set_show_right_answer", bundleOf())
    }

    //запуск теста
    fun startTest(mode: String) {
        logEvent("start_test_${mode}", bundleOf())
    }

    //закрыли диалог предложения прорешать ошибки
    fun closeManyError() {
        logEvent("close_many_error_dialog", bundleOf())
    }

    //открыли список тестов из предложения
    fun manyErrorDialogClickErrorList() {
        logEvent("many_error_click_error_list", bundleOf())
    }

    //запустили тест с ошибками из предложения
    fun manyErrorDialogClickStartTest() {
        logEvent("many_error_click_start_test", bundleOf())
    }

    //Не помечать вопросы с множественным вариантом ответа
    fun setMultipleAnswerChoice(tagMultipleAnswerChoice: Boolean) {
        if (!tagMultipleAnswerChoice) return
        logEvent("start_testing_set_multiple_answer", bundleOf())
    }

    //заход со сборки
    fun openAppFlavour() {
        logEvent("open_app_flavour", bundleOf())
    }

    fun trackScreenEvent(screenName: String) {
        val bundle = Bundle().apply {
            putString("screen_name", screenName)
        }
        logEvent("screen_view", bundle)
    }

    private fun logEvent(name: String, bundle: Bundle = bundleOf()) {
        Timber.tag("Analytics").i("name = $name, data = $bundle")
    }

    fun firstLaunch(firstLaunchDate: String) {
        if (firstLaunchDate.isEmpty()) {
            logEvent("first_launch_app", bundleOf())
        }
    }

    fun manualCreateFromFile() {
        logEvent("manual_create_from_file")
    }

    fun manualCreateFromJson() {
        logEvent("manual_create_from_json")
    }

    fun manualClose() {
        logEvent("manual_close")
    }

    fun manualCopyPromtFile() {
        logEvent("manual_copy_promt_file")
    }

    fun manualCopyPromtUrl() {
        logEvent("manual_copy_promt_url")
    }

    fun manualStartMovie() {
        logEvent("manual_start_movie")
    }

    fun manualClickDeepSeek() {
        logEvent("manual_click_deepseek")
    }

    fun errorCreateTestFromFile() {
        logEvent("error_create_test_from_file")
    }

    //поделиться тестом
    fun testManagerShareTest() {
        logEvent("share_test")
    }

    //принудительное завершение теста через кнопку «Назад»
    fun forceFinishTest() {
        logEvent("force_finish_test")
    }

    //пользователь зашёл без интернета или потерял соединение
    fun noInternet() {
        logEvent("no_internet")
    }

    //показ страницы онбординга 1
    fun onboardingPageShown1() {
        logEvent("onboarding_page_shown_1")
    }

    //показ страницы онбординга 2
    fun onboardingPageShown2() {
        logEvent("onboarding_page_shown_2")
    }

    //показ страницы онбординга 3
    fun onboardingPageShown3() {
        logEvent("onboarding_page_shown_3")
    }

    //пропуск онбординга
    fun onboardingSkip() {
        logEvent("onboarding_skip")
    }

    //завершение онбординга
    fun onboardingComplete() {
        logEvent("onboarding_complete")
    }

    //фильтрация ответов: все
    fun testResultFilterAll() {
        logEvent("test_result_filter_all")
    }

    //фильтрация ответов: правильные
    fun testResultFilterCorrect() {
        logEvent("test_result_filter_correct")
    }

    //фильтрация ответов: неправильные
    fun testResultFilterIncorrect() {
        logEvent("test_result_filter_incorrect")
    }

    //закрытие баннера Telegram
    fun telegramBannerClosed() {
        logEvent("telegram_banner_closed")
    }

    //клик по баннеру Telegram
    fun telegramBannerClicked() {
        logEvent("telegram_banner_clicked")
    }

    //выбор языка
    fun selectLanguage(languageCode: String) {
        logEvent("select_lang_$languageCode")
    }

    //переход на экран "Почему я вижу рекламу?" из профиля
    fun openAdExplanation() {
        logEvent("open_ad_explanation")
    }

    //клик по кнопке "Оставить отзыв"
    fun openReview() {
        logEvent("open_review")
    }

    //закрытие баннера объяснения рекламы на главной
    fun adExplanationBannerClosed() {
        logEvent("ad_explanation_banner_closed")
    }

    //клик по баннеру объяснения рекламы на главной
    fun adExplanationBannerClicked() {
        logEvent("ad_explanation_banner_clicked")
    }
}
