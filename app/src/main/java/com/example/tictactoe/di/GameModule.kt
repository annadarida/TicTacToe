package com.example.tictactoe.di

import com.example.domains.usecases.GetPlayersUseCase
import com.example.tictactoe.ui.game.GameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val gameModule = module {
    includes(repositoryModule)
    factory<GetPlayersUseCase> { GetPlayersUseCase(get()) }

    viewModel<GameViewModel> { GameViewModel(get()) }
}