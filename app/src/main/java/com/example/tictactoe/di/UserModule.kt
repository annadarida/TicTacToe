package com.example.tictactoe.di

import com.example.domains.repository.PlayerRepository
import com.example.domains.usecases.SavePlayersUseCase
import com.example.tictactoe.data.repository.PlayerRepositoryImplementation
import com.example.tictactoe.ui.user.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    includes(repositoryModule)
    factory<SavePlayersUseCase> { SavePlayersUseCase(get()) }

    viewModel<UserViewModel> { UserViewModel(get()) }
}