package ru.project.tutor.repository

import ru.project.tutor.database.dao.AnswerChoiceDao
import ru.project.tutor.database.entity.AnswerChoiceEntity
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.repository.AnswerChoiceRepository

class AnswerChoiceRepositoryImpl(
    private val answerChoiceDao: AnswerChoiceDao
) : AnswerChoiceRepository {

    override suspend fun createAnswerChoiceList(
        answers: List<AnswerChoiceData>
    ): List<Int> {
        val entities = answers.map { data ->
            AnswerChoiceEntity(
                id = data.id,
                answerChoice = data.text,
                isRightAnswer = data.isRightAnswer,
                questionId = data.questionId
            )
        }
        val ids = answerChoiceDao.insert(entities)
        return ids.map { it.toInt() }
    }

    override suspend fun getAnswerChoiceList(
        questionId: Int
    ): List<AnswerChoiceData> {
        val entities = answerChoiceDao.getByQuestionId(questionId)
        return entities.map { entity ->
            AnswerChoiceData(
                id = entity.id,
                text = entity.answerChoice,
                isRightAnswer = entity.isRightAnswer,
                questionId = questionId
            )
        }
    }

    override suspend fun deleteAnswerChoicesByQuestionId(questionId: Int) {
        answerChoiceDao.deleteByQuestionId(questionId)
    }
}