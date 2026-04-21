package ru.project.tutor.common_ui.composable.utils

interface UseCase<T, R> : UseCaseCore {
    operator fun invoke(params: T): R
}

interface UseCaseCore