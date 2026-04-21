package ru.project.tutor.notifications

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NotificationMessagesTest {

    @Test
    fun `message index 0 uses test count and routes to TESTS_NORMAL`() {
        val msg = NotificationMessages.get(
            index = 0,
            testCount = 5,
            errorCount = 3,
            lastTestName = "Биология"
        )!!
        assertEquals("Время практиковаться!", msg.title)
        assert(msg.body.contains("5"))
        assertEquals(NotificationDestination.TESTS_NORMAL, msg.destination)
    }

    @Test
    fun `message index 1 uses error count and last test name, routes to TESTS_ERRORS`() {
        val msg = NotificationMessages.get(
            index = 1,
            testCount = 5,
            errorCount = 7,
            lastTestName = "Биология"
        )!!
        assertEquals("Ошибки ждут исправления", msg.title)
        assert(msg.body.contains("7"))
        assert(msg.body.contains("Биология"))
        assertEquals(NotificationDestination.TESTS_ERRORS, msg.destination)
    }

    @Test
    fun `message index 1 skipped when error count is 0, falls through to next valid message`() {
        val msg = NotificationMessages.get(
            index = 1,
            testCount = 5,
            errorCount = 0,
            lastTestName = "Биология"
        )!!
        assertNotEquals(NotificationDestination.TESTS_ERRORS, msg.destination)
    }

    @Test
    fun `message index 3 skipped when error count is 0`() {
        val msg = NotificationMessages.get(
            index = 3,
            testCount = 5,
            errorCount = 0,
            lastTestName = "Биология"
        )!!
        assertNotEquals(NotificationDestination.TESTS_ERRORS, msg.destination)
    }

    @Test
    fun `returns null when test count is 0`() {
        val msg = NotificationMessages.get(
            index = 0,
            testCount = 0,
            errorCount = 0,
            lastTestName = ""
        )
        assertNull(msg)
    }

    @Test
    fun `nextIndex wraps around after last index`() {
        assertEquals(0, NotificationMessages.nextIndex(5))
    }

    @Test
    fun `nextIndex increments within pool`() {
        assertEquals(1, NotificationMessages.nextIndex(0))
        assertEquals(3, NotificationMessages.nextIndex(2))
    }

    @Test
    fun `getNoTestsMessage returns correct Russian text and TEST_FACTORY destination`() {
        val msg = NotificationMessages.getNoTestsMessage()
        assertEquals("Начните своё обучение!", msg.title)
        assertEquals(
            "Создайте первый тест и начните практиковаться прямо сейчас.",
            msg.body
        )
        assertEquals(NotificationDestination.TEST_FACTORY, msg.destination)
    }
}
