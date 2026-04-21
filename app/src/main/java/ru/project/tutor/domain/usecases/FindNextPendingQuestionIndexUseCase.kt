package ru.project.tutor.domain.usecases

import ru.project.tutor.ui.screen.test_process.TestProcessStore
import ru.project.tutor.ui.screen.test_process.composable.QuestionStatus

class FindNextPendingQuestionIndexUseCase {
    operator fun invoke(
        state: TestProcessStore.State,
    ): Int {
        return state.questions.indexOfFirst {
            it.questionStatus != QuestionStatus.CORRECT &&
                    it.questionStatus != QuestionStatus.ERROR
        }
    }
}