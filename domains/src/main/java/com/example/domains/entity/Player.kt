package com.example.domains.entity

data class Player(
    val name: String,
    var score: Int = 0,
    val isX: Boolean = false
)
