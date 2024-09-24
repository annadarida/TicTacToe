package com.example.tictactoe.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domains.entity.ScoreClass
import com.example.tictactoe.entity.Player
import com.example.tictactoe.entity.Scores

class ScoreViewModel: ViewModel() {

    private var _player1: MutableLiveData<Player?> = MutableLiveData()
    private var _player2: MutableLiveData<Player?> = MutableLiveData()
    val player1: LiveData<Player?>
        get() = _player1
    val player2: LiveData<Player?>
        get() = _player2

    val _scoreList: MutableLiveData<List<ScoreClass>> = MutableLiveData()
    val scoreList: LiveData<List<ScoreClass>>
        get() = _scoreList

    fun setScoreList(scores: Scores) {
        _scoreList.value = scores.list
    }

    fun setUpPlayers(player1: Player, player2: Player) {
        _player1.value = player1
        _player2.value = player2
    }

}