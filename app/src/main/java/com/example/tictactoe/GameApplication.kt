package com.example.tictactoe

import android.app.Application
import com.example.tictactoe.di.gameModule
import com.example.tictactoe.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GameApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GameApplication)
            modules(
                listOf(
                    userModule, gameModule
                )
            )
        }
    }
}