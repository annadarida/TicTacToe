package com.example.tictactoe

data class UpdateCell(
    val row: Int,
    val column: Int,
    val isX: Boolean,
    val shouldClearAllGame: Boolean = false
)
