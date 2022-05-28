package com.example.polycareer

import android.app.Application
import com.example.polycareer.di.core.databaseModule
import com.example.polycareer.di.core.networkModule
import com.example.polycareer.di.core.repositoryModule
import com.example.polycareer.di.screen.authModule
import com.example.polycareer.di.screen.mainModule
import com.example.polycareer.di.screen.quizModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        const val CURRENT_USER_ID = "CURRENT_USER_ID"
        const val USER_ID_KEY = "user_id"
        const val USER_ID_DEFAULT_VALUE: Long = -1

        const val IS_FIRST_OPEN = "FIRST_OPEN"
        const val FIRST_OPEN_KEY = "first_open"

        const val IS_DARK_MODE = "DARK_MODE"
        const val DARK_MODE_KEY = "dark_mode"

        const val TRY_NUMBER = "try_number"
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                authModule,
                quizModule,
                mainModule
            )
        }
    }
}