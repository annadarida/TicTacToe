package com.example.tictactoe.di

import com.example.domains.repository.PlayerRepository
import com.example.tictactoe.data.repository.PlayerRepositoryImplementation
import org.koin.dsl.module

val repositoryModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImplementation.getInstance()
    }
}