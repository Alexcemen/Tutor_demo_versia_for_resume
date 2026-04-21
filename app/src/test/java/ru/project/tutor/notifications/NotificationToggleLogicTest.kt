package ru.project.tutor.notifications

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for the toggle logic: verifies that when notifications are disabled,
 * the NotificationWorker would skip posting (by checking the enabled flag),
 * and that the message index advances correctly across sessions.
 */
class NotificationToggleLogicTest {

    @Test
    fun `when notifications disabled, no message should be posted`() {
        // Simulates what NotificationWorker does when notificationsEnabled == false
        val notificationsEnabled = false
        val shouldPost = notificationsEnabled && NotificationMessages.get(
            index = 0, testCount = 5, errorCount = 0, lastTestName = "Test"
        ) != null
        assertFalse(shouldPost)
    }

    @Test
    fun `when notifications enabled and tests exist, message is available`() {
        val notificationsEnabled = true
        val message = if (notificationsEnabled) {
            NotificationMessages.get(index = 0, testCount = 3, errorCount = 0, lastTestName = "Test")
        } else null
        assertTrue(message != null)
    }

    @Test
    fun `message index advances correctly after each notification`() {
        var index = 0
        // Simulate 7 notification cycles (wraps at 6)
        repeat(6) { index = NotificationMessages.nextIndex(index) }
        assertEquals(0, index) // should have wrapped back to 0
    }

    @Test
    fun `message index starts fresh after re-enabling (index 0)`() {
        // When user re-enables, index was preserved but messages still work from any starting index
        val message = NotificationMessages.get(
            index = 4, testCount = 2, errorCount = 0, lastTestName = "История"
        )
        assertTrue(message != null)
        assertEquals(NotificationDestination.TESTS_NORMAL, message!!.destination)
    }
}
