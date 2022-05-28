package com.example.polycareer.data.repository

import com.example.polycareer.data.database.dao.QuizDao
import com.example.polycareer.data.database.model.AnswersEntity
import com.example.polycareer.data.database.model.CoeffsEntity
import com.example.polycareer.data.database.model.UsersAnswersEntity
import com.example.polycareer.domain.model.QuestionsApiResponse
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.exception.DatabaseException

class QuizItemsLocalRepository(
    private val quizDao: QuizDao
) {
    var currentTryNumber = 0L

    suspend fun deleteUnfinishedTests(): Boolean =
        try {
            quizDao.deleteUnfinishedTests()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    suspend fun getAllQuestions(): QuestionsResponse {
        val questions = runSafety { getAllQuestions() }

        val result = mutableListOf<MutableList<String>>()
        for (question in questions) {
            if (result.size - 1 < question.questionId) {
                result.add(mutableListOf())
            }
            result[question.questionId].add(question.text)
        }

        if (result.isNotEmpty()) {
            return QuestionsResponse(result)
        } else {
            throw DatabaseException()
        }
    }

    private suspend fun <T> runSafety(daoFunction: suspend QuizDao.() -> T): T = try {
        quizDao.daoFunction()
    } catch (e: Exception) {
        throw DatabaseException()
    }

    suspend fun setQuestions(answers: QuestionsApiResponse): Boolean = try {
        quizDao.deleteAllQuestions()
        quizDao.deleteAllCoeffs()
        val resultQuestions = mutableListOf<AnswersEntity>()
        val answersSource = answers.quiz
        for (answer in answersSource) {
            val questionId = answersSource.indexOf(answer)
            var answerIndex = 0
            for (unit in answer.entries) {
                resultQuestions.add(
                    AnswersEntity(
                        id = unit.key.toLong(),
                        questionId = questionId,
                        answerIndex = answerIndex,
                        text = unit.value
                    )
                )
                answerIndex++
            }
        }
        quizDao.setAllQuestions(resultQuestions)

        val coeffsSource = answers.matrix
        val profSource = answers.prof
        val resultCoeffs = mutableListOf<CoeffsEntity>()
        var i = 0
        for (list in coeffsSource) {
            resultCoeffs.add(
                CoeffsEntity(
                    answerId = resultCoeffs.size.toLong(),
                    yk = list[0],
                    ytc = list[1],
                    inn = list[2],
                    ivt = list[3],
                    pi1 = list[4],
                    pi2 = list[5],
                    cic = list[6],
                    ic = list[7],
                    fi = list[8],
                    mot = list[9],
                    profession = profSource[i]
                )
            )
            i++
        }
        quizDao.setAllCoeffs(resultCoeffs)
        true
    } catch (e: Exception) {
        false
    }

    suspend fun saveUserAnswer(questionId: Long, answerIndex: Long, userId: Long): Boolean = try {
        if (questionId == 0L) {
            deleteUnfinishedTests()
            currentTryNumber = quizDao.getCountOfUsersAttempts(userId) + 1
        }
        val answerId = quizDao.getAnswerIdByQuestionIdAndAnswerIndex(
            questionId = questionId,
            answerIndex = answerIndex
        )

        quizDao.insertNewAnswer(
            UsersAnswersEntity(
                userId = userId,
                answerId = answerId,
                tryNumber = currentTryNumber,
                time = System.currentTimeMillis()
            )
        )
        true
    } catch (e: Exception) {
        false
    }

    suspend fun clearUsersLastAttemptAnswers(userId: Long): Boolean = try {
        quizDao.deleteAnswersByUsersTryNumber(
            userId = userId,
            tryNumber = currentTryNumber
        )
        true
    } catch (e: Exception) {
        false
    }

    suspend fun clearUsersLastAnswer(userId: Long): Boolean = try {
        quizDao.deleteUsersLastAnswer(
            userId = userId
        )
        true
    } catch (e: Exception) {
        false
    }

}
