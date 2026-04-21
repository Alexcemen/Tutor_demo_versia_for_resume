package ru.project.tutor.domain.models.card_info

data class CardInfoStatistics(
    val countCardBlocks: Int = 0,
    val timeSpendSeconds: Int = 0,
    val countTrue: Int = 0,
    val countFalse: Int = 0
)