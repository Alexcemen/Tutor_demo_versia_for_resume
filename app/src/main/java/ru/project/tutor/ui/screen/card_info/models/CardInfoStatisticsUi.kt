package ru.project.tutor.ui.screen.card_info.models

data class CardInfoStatisticsUi(
    val countCardBlocks: Int,
    val timeSpendSeconds: Int,
    val countTrue: Int,
    val countFalse: Int
)