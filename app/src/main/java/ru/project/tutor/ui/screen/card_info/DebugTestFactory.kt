package ru.project.tutor.ui.screen.card_info

import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.repository.AnswerChoiceRepository
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.domain.repository.TestRepository

class DebugTestFactory(
    private val testRepository: TestRepository,
    private val questionRepository: QuestionRepository,
    private val answerChoiceRepository: AnswerChoiceRepository,
) {
    suspend fun createDebugTest() {
        val testId = testRepository.createTest(title = "Тест для проверки")

        createSingleAnswerQuestion(
            testId = testId,
            question = "В данном вопросе должен быть один правильный вариант ответа. Нужно ответить неправильно",
            answers = listOf(
                "Неверный" to false,
                "Верный" to true,
                "Неверный" to false,
                "Неверный" to false,
            )
        )

        createSingleAnswerQuestion(
            testId = testId,
            question = "Это очень длинный вопрос, содержащий в себе огромное количество текста, специально созданного для проверки поведения UI, скролла, ограничения размеров, корректности сжатия текста, а также тестирования базы данных на хранение больших строк. Если этот текст ломает верстку или превышает лимиты длины, значит баг найден. Повторяем: это длинный тестовый вопрос.",
            answers = listOf(
                "Короткий" to false,
                "Длинный текст ответа который тоже проверяет максимальную длину строки в базе данных и UI компонента" to true,
            )
        )

        createSingleAnswerQuestion(
            testId = testId,
            question = "В этом вопросе несколько верных вариантов ответа. Нужно ответить на все верно",
            answers = listOf(
                "Верный" to true,
                "Верный" to true,
                "Неверный" to false,
                "Верный" to true,
            )
        )

        createSingleAnswerQuestion(
            testId = testId,
            question = "В этом вопросе несколько верных вариантов ответа. Нужно выбрать только два верных варианта ответа",
            answers = listOf(
                "Верный" to true,
                "Верный" to true,
                "Неверный" to false,
                "Верный" to true,
            )
        )

        createSingleAnswerQuestion(
            testId = testId,
            question = "В этом вопросе несколько верных вариантов ответа. Нужно выбрать только неверный",
            answers = listOf(
                "Верный" to true,
                "Верный" to true,
                "Неверный" to false,
                "Верный" to true,
            )
        )

        createSingleAnswerQuestion(
            testId = testId,
            question = "В этом вопросе несколько верных вариантов ответа. Нужно выбрать один верный и один не верный",
            answers = listOf(
                "Верный" to true,
                "Верный" to true,
                "Неверный" to false,
                "Верный" to true,
            )
        )

        createSingleAnswerQuestion(
            testId = testId,
            question = "В данном вопросе должен быть один правильный вариант ответа. Нужно ответить верно",
            answers = listOf(
                "Неверный" to false,
                "Верный" to true,
                "Неверный" to false,
                "Неверный" to false,
            )
        )

        val longText = "Это очень длинный вопрос, содержащий в себе огромное количество текста, " +
                "специально созданного для проверки поведения UI, скролла, ограничения размеров, " +
                "корректности сжатия текста, а также тестирования базы данных на хранение больших " +
                "строк. Если этот текст ломает верстку или превышает лимиты длины, значит баг " +
                "найден. Повторяем: это длинный тестовый вопрос."
        val longQuestion = List(6) { longText }.joinToString("\n")

        val longAnswer = List(8) {
            "Длинный текст ответа который тоже проверяет максимальную длину строки в базе данных и UI компонента"
        }.joinToString("\n")

        createSingleAnswerQuestion(
            testId = testId,
            question = longQuestion,
            answers = listOf(
                longAnswer to true,
                longAnswer to true,
                "Короткий" to false,
                longAnswer to true,
            )
        )
    }

    private suspend fun createSingleAnswerQuestion(
        testId: Int,
        question: String,
        answers: List<Pair<String, Boolean>>,
    ) {
        val questionId = questionRepository.createQuestion(
            question = question,
            testId = testId,
        )
        answerChoiceRepository.createAnswerChoiceList(
            answers = answers.map { (text, isRight) ->
                AnswerChoiceData(
                    id = 0,
                    text = text,
                    isRightAnswer = isRight,
                    questionId = questionId
                )
            }
        )
    }
}
