package ru.project.tutor.ui.screen.test_info.composable.models

data class UserStatisticsUi(
    val firstLaunchDate: String,
    val allCountTestCreated: Int,
    val allCountErrors: Int,
    val allCountTestsCompleted: Int,
    val allCountTime: String,
)