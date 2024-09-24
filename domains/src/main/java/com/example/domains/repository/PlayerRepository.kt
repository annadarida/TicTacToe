package com.example.domains.repository

import com.example.domains.entity.Player

interface PlayerRepository {
    fun savePlayers(player1: Player, player2: Player)

    fun getPlayers(): Pair<Player, Player>
}