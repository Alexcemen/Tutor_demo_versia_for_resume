package ru.project.tutor.common_ui.composable.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class FormatTimeTest {

    @Test
    fun shouldReturnMinutes() {
        val allCountTime = 90000L

        val actual = formatTime(allCountTime)
        val expected = "1м30с"
        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnHours() {
        val allCountTime = 7200000L

        val actual = formatTime(allCountTime)
        val expected = "2ч0м"
        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnSeconds() {
        val allCountTime = 58000L

        val actual = formatTime(allCountTime)
        val expected = "58с"
        assertEquals(expected, actual)
    }
}