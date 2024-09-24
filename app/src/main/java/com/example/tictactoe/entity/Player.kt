package com.example.tictactoe.entity

import java.io.Serializable

data class Player(
    val name: String,
    var score: Int = 0,
    val isX: Boolean = false,
    val sign: Int = if (isX) 1 else 2
): Serializable
