package ru.project.tutor.domain.usecases

import kotlinx.serialization.json.Json
import ru.project.tutor.domain.repository.AttemptRepository
import ru.project.tutor.ui.models.QuestionForTestProcessUi
import ru.project.tutor.utils.withIO

class CreateAttemptUseCase(
    private val attemptRepository: AttemptRepository,
) {
    suspend operator fun invoke(
        testId: Int,
        questions: List<QuestionForTestProcessUi>,
    ): Int = withIO {
        val questionJson = Json.encodeToString(questions)
        attemptRepository.createAttempt(
            testId = testId,
            questions = questionJson
        )
    }
}