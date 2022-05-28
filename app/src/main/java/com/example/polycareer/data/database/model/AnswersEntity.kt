package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class AnswersEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "answer_index") val answerIndex: Int,
    @ColumnInfo(name = "question_id") val questionId: Int,
    @ColumnInfo val text: String,
)
