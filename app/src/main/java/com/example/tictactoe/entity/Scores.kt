package com.example.tictactoe.entity

import com.example.domains.entity.ScoreClass
import java.io.Serializable

data class Scores(
    val list: List<ScoreClass>
): Serializable
