package com.example.polycareer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.polycareer.data.database.dao.DirectionsDao
import com.example.polycareer.data.database.dao.ProfessionsDao
import com.example.polycareer.data.database.dao.QuizDao
import com.example.polycareer.data.database.dao.UserDao
import com.example.polycareer.data.database.model.*

@Database(entities = [UserEntity::class, GradesEntity::class,
    AnswersEntity::class, UsersAnswersEntity::class, CoeffsEntity::class,
    DirectionEntity::class, ProfessionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val quizDao: QuizDao
    abstract val directionsDao: DirectionsDao
    abstract val professionsDao: ProfessionsDao
}
