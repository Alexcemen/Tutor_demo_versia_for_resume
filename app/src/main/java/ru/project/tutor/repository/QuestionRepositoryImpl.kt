package ru.project.tutor.repository

import ru.project.tutor.database.dao.QuestionDao
import ru.project.tutor.database.entity.QuestionEntity
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.domain.repository.AnswerChoiceRepository
import ru.project.tutor.domain.repository.QuestionRepository

class QuestionRepositoryImpl(
    private val questionDao: QuestionDao,
    private val answerChoiceRepository: AnswerChoiceRepository,
) : QuestionRepository {

    override suspend fun createQuestion(
        question: String,
        testId: Int,
    ) = questionDao.insert(
        QuestionEntity(
            question = question,
            testId = testId,
            position = getMaxPosition(testId) + 1,
        )
    ).toInt()

    override suspend fun getQuestionsWithAnswersByTestId(testId: Int): List<QuestionWithAnswersData> {
        val questions = questionDao.getQuestionsByTestId(testId)
        return questions.map { questionEntity ->
            val answers = answerChoiceRepository.getAnswerChoiceList(questionEntity.id)
            mapQuestionEntityToData(questionEntity, answers)
        }
    }

    override suspend fun getQuestionWithAnswersByQuestionId(questionId: Int): QuestionWithAnswersData? {
        val questionEntity = questionDao.get(questionId) ?: return null
        val answers = answerChoiceRepository.getAnswerChoiceList(questionEntity.id)
        return mapQuestionEntityToData(questionEntity, answers)
    }

    override suspend fun deleteQuestion(questionId: Int, testId: Int) {
        val deletedPosition = getPositionByQuestionId(questionId = questionId)

        questionDao.deleteById(questionId = questionId)
        updatePosition(
            testId = testId,
            deletedPosition = deletedPosition
        )
    }

    override suspend fun updateQuestion(
        questionId: Int,
        question: String,
    ) {
        questionDao.updateQuestionText(
            questionId = questionId,
            question = question,
        )
    }

    override suspend fun getMaxPosition(testId: Int): Int {
        return questionDao.getMaxPosition(testId = testId) ?: 0
    }

    override suspend fun updatePosition(testId: Int, deletedPosition: Int) {
        questionDao.updatePositions(testId = testId, deletedPosition = deletedPosition)
    }

    override suspend fun getPositionByQuestionId(questionId: Int): Int {
        return questionDao.getPositionByQuestionId(questionId = questionId)
    }

    private fun mapQuestionEntityToData(
        questionEntity: QuestionEntity,
        answerChoiceDataList: List<AnswerChoiceData>,
    ): QuestionWithAnswersData {
        return QuestionWithAnswersData(
            question = QuestionData(
                id = questionEntity.id,
                questionText = questionEntity.question,
                testId = questionEntity.testId,
                position = questionEntity.position,
                isMultipleAnswerChoice = answerChoiceDataList.count { it.isRightAnswer } > 1
            ),
            answers = answerChoiceDataList
        )
    }
}