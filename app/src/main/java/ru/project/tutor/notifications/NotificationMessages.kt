package ru.project.tutor.notifications

enum class NotificationDestination {
    TESTS_NORMAL,
    TESTS_ERRORS,
    TEST_FACTORY,
}

data class NotificationMessage(
    val title: String,
    val body: String,
    val destination: NotificationDestination,
)

object NotificationMessages {

    private const val POOL_SIZE = 6

    fun get(
        index: Int,
        testCount: Int,
        errorCount: Int,
        lastTestName: String,
    ): NotificationMessage? {
        if (testCount == 0) return null

        for (offset in 0 until POOL_SIZE) {
            val i = (index + offset) % POOL_SIZE
            val candidate = build(i, testCount, errorCount, lastTestName)
            if (candidate != null) return candidate
        }
        return null
    }

    fun nextIndex(currentIndex: Int): Int = (currentIndex + 1) % POOL_SIZE

    fun getNoTestsMessage(): NotificationMessage = NotificationMessage(
        title = "Начните своё обучение!",
        body = "Создайте первый тест и начните практиковаться прямо сейчас.",
        destination = NotificationDestination.TEST_FACTORY,
    )

    private fun build(
        index: Int,
        testCount: Int,
        errorCount: Int,
        lastTestName: String,
    ): NotificationMessage? = when (index) {
        0 -> NotificationMessage(
            title = "Время практиковаться!",
            body = "У вас $testCount тестов. Не давайте им пылиться.",
            destination = NotificationDestination.TESTS_NORMAL,
        )
        1 -> if (errorCount > 0) NotificationMessage(
            title = "Ошибки ждут исправления",
            body = "В «$lastTestName» есть $errorCount ошибок — пора разобраться.",
            destination = NotificationDestination.TESTS_ERRORS,
        ) else null
        2 -> NotificationMessage(
            title = "Давно не виделись",
            body = "Вернитесь и пройдите короткий тест — займёт пару минут.",
            destination = NotificationDestination.TESTS_NORMAL,
        )
        3 -> if (errorCount > 0) NotificationMessage(
            title = "Ошибок становится больше",
            body = "Вы накопили $errorCount ошибок по всем тестам.",
            destination = NotificationDestination.TESTS_ERRORS,
        ) else null
        4 -> NotificationMessage(
            title = "Напоминание",
            body = "«$lastTestName» ждёт вас.",
            destination = NotificationDestination.TESTS_NORMAL,
        )
        5 -> NotificationMessage(
            title = "Готовы к проверке?",
            body = "У вас $testCount тестов — самое время себя испытать.",
            destination = NotificationDestination.TESTS_NORMAL,
        )
        else -> null
    }
}
