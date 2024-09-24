package com.example.tictactoe.data.repository

import com.example.domains.entity.Player
import com.example.domains.repository.PlayerRepository

class PlayerRepositoryImplementation : PlayerRepository {

    // Runtime memory to store player1 and player2
    private lateinit var player1: Player
    private lateinit var player2: Player

    // Save players in memory
    override fun savePlayers(player1: Player, player2: Player) {
        this.player1 = player1
        this.player2 = player2
    }

    // Retrieve players from memory
    override fun getPlayers(): Pair<Player, Player> {
        return Pair(player1, player2)
    }

    // Singleton pattern to ensure a single instance across the app
    companion object {
        @Volatile
        private var instance: PlayerRepositoryImplementation? = null

        fun getInstance(): PlayerRepositoryImplementation {
            return instance ?: synchronized(this) {
                instance ?: PlayerRepositoryImplementation().also { instance = it }
            }
        }
    }
}
