package com.example.domains.entity

import java.io.Serializable

data class ScoreClass(
    val scoreX: Int,
    val playerX: String,
    val scoreO: Int,
    val playerO: String
): Serializable
