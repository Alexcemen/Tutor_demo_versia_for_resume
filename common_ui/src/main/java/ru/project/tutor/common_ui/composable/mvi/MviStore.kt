package ru.project.tutor.common_ui.composable.mvi

/**
 * Состояние экрана
 */
interface MviState

/**
 * Намерения пользователя
 */
interface MviEvent

/**
 * Обновление стейта
 */
interface MviEffect

/**
 * Сайд-эффекты (одноразовые события)
 */
interface MviSideEffect

/**
 * Состояние экрана (View)
 */
interface MviUiState

/**
 * Редуктор для обработки интентов
 */
interface Reducer<S : MviState, UiState: MviUiState> {
    fun reduce(state: S): UiState
}