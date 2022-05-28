package com.example.polycareer.di.core

import android.content.Context
import androidx.room.Room
import com.example.polycareer.data.database.AppDatabase
import com.example.polycareer.data.database.dao.DirectionsDao
import com.example.polycareer.data.database.dao.ProfessionsDao
import com.example.polycareer.data.database.dao.QuizDao
import com.example.polycareer.data.database.dao.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module(createdAtStart = true) {
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "user_database")
            .build()
    }

    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao
    }

    fun provideQuizDao(database: AppDatabase): QuizDao {
        return database.quizDao
    }

    fun provideDirectionsDao(database: AppDatabase): DirectionsDao {
        return database.directionsDao
    }

    fun provideProfessionsDao(database: AppDatabase): ProfessionsDao {
        return database.professionsDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
    single { provideQuizDao(get()) }
    single { provideDirectionsDao(get()) }
    single { provideProfessionsDao(get()) }
}