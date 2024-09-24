package com.example.tictactoe.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.domains.entity.ScoreClass
import com.example.domains.usecases.GetPlayersUseCase
import com.example.tictactoe.entity.Player
import com.example.tictactoe.UpdateCell
import com.example.tictactoe.entity.Scores
import com.example.tictactoe.ui.menu.MenuFragmentDirections
import com.example.tictactoe.ui.menu.NavigationType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GameViewModel(
        val getPlayersUseCase: GetPlayersUseCase
    ): ViewModel() {

    private var _player1: MutableLiveData<Player?> = MutableLiveData()
    private var _player2: MutableLiveData<Player?> = MutableLiveData()
    val player1: LiveData<Player?>
        get() = _player1
    val player2: LiveData<Player?>
        get() = _player2

    private var _instructionString = MutableLiveData("")
    val instructionString: LiveData<String>
        get() = _instructionString

    private var _scoreData: MutableLiveData<ScoreClass?> = MutableLiveData()
    val scoreData: LiveData<ScoreClass?>
        get() = _scoreData

    private var gameBoard: MutableList<MutableList<Int>> = mutableListOf(
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0)
    )

    private var _scoreList = arrayListOf<ScoreClass>()

    private var _updateCell = MutableLiveData<UpdateCell>()
    val updateCell: LiveData<UpdateCell>
        get() = _updateCell

    private var _navigateAction = MutableLiveData<NavDirections?>()
    val navigateAction: LiveData<NavDirections?>
        get() = _navigateAction

    private var currentPlayer: Player? = null
    var rounds = 0

    private val disposables = CompositeDisposable()

    fun setUpPlayers() {
        disposables.add(
            getPlayersUseCase.buildUseCase(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { (domainPlayer1, domainPlayer2) ->
                    val appPlayer1 = Player(
                        name = domainPlayer1.name,
                        score = domainPlayer1.score,
                        isX = domainPlayer1.isX
                    )

                    val appPlayer2 = Player(
                        name = domainPlayer2.name,
                        score = domainPlayer2.score,
                        isX = domainPlayer2.isX
                    )

                    Pair(appPlayer1, appPlayer2) // Return the mapped Pair
                }
                .subscribe { pair ->
                    _player1.postValue(pair.first)
                    _player2.postValue(pair.second)
                    currentPlayer = pair.first
                    displayInstructionText()
                }
        )
    }

    fun isGameFinished(): Boolean {
        //In case somebody won game is finished
        for (i in 0..4) {
            if (gameBoard[0][i] == 3 || gameBoard[4][i] == 3) {
                return true
            }
        }
        for (i in 1..3) {
            if (gameBoard[i][0] == 3 || gameBoard[i][4] == 3) {
                return true
            }
        }
        //In case there is still space left and nobody won yet the game continues
        for (i in 0..4) {
            for (j in 1..3) {
                if (gameBoard[i][j] == 0) {
                    return false
                }
            }
        }
        return true
    }

    fun startGame() {
        currentPlayer = if (rounds % 2 == 0) player1.value else player2.value
        _instructionString.postValue("""Choose where to put your sign ${currentPlayer?.name}""")
    }

    fun chooseSpace(row: Int, column: Int) {
        //if the space is not taken
        if (gameBoard[row][column] == 0) {
            gameBoard[row][column] = if (currentPlayer?.isX == true) 1 else 2
            updateScore(row, column, currentPlayer?.isX == true)
            _updateCell.value = UpdateCell(row, column, currentPlayer?.isX == true)
            rounds++
            currentPlayer = if (rounds % 2 == 0) player1.value else player2.value
            displayInstructionText()
            if (isGameFinished()) {
                calculateScore()
                _updateCell.value = UpdateCell(row, column, currentPlayer?.isX == true, true)
            }
        } //ToDo: else implement error message
    }

    fun displayInstructionText() {
        _instructionString.value = """Choose where to put your sign ${currentPlayer?.name}"""
    }

    fun calculateScore() {
        //Calculate scores
        var roundPlayerX = 0
        var roundPlayerO = 0
        val playerX: Player?
        val playerO: Player?
        if (_player1.value?.isX == true) {
            Log.d("**GAME**", "Player1 is X" )
            playerX = _player1.value
            playerO = _player2.value
        } else {
            Log.d("**GAME**", "Player2 is X" )
            playerX = _player2.value
            playerO = _player1.value

        }
        Log.d("**GAME**", "Player1 is ${player1.value?.name} Player 2 is ${player2.value?.name}" )
        Log.d("**GAME**", "PlayerX is ${playerX?.name} Player O is ${playerO?.name}" )

        for (i in 0..4) {
            if (gameBoard[0][i] == 3) roundPlayerX++
            if (gameBoard[4][i] == 3) roundPlayerO++
        }
        for (j in 1..3) {
            if (gameBoard[j][0] == 3) roundPlayerX++
            if (gameBoard[j][4] == 3) roundPlayerO++
        }
        if (roundPlayerX > roundPlayerO) {
            playerX?.let {
                it.score++
            }
        } else if (roundPlayerO > roundPlayerX) {
            playerO?.let {
                it.score++
            }
        } else {
            _instructionString.value =
                "Everybody wins! It's a draw with a score of ${roundPlayerX}-${roundPlayerO}"
        }
        showScoreOfPlayers(roundPlayerX, roundPlayerO, playerX?.name ?: "", playerO?.name ?: "")
    }

    fun showScoreOfPlayers(roundX: Int, roundO: Int, nameX: String, nameO: String) {
        val score = ScoreClass(roundX, nameX, roundO, nameO)
        _scoreData.value = score
        val list = _scoreList
        list.add(score)
        _scoreList = list
    }

    fun updateScore(row: Int, col: Int, isX: Boolean) {
        if (isX) {
            gameBoard[0][col]++
            gameBoard[row][0]++
            if (row == col) gameBoard[0][0]++
            if (row + col == 4) gameBoard[0][4]++
        } else {
            gameBoard[4][col]++
            gameBoard[row][4]++
            if (row == col) gameBoard[4][4]++
            if (row + col == 4) gameBoard[4][0]++
        }
    }

    fun wipeGameBoard() {
        gameBoard = mutableListOf(
            mutableListOf(0, 0, 0, 0, 0),
            mutableListOf(0, 0, 0, 0, 0),
            mutableListOf(0, 0, 0, 0, 0),
            mutableListOf(0, 0, 0, 0, 0),
            mutableListOf(0, 0, 0, 0, 0)
        )
    }

    fun clearGame() {
        _scoreData.value = null
        _updateCell.value = UpdateCell(0, 0, false, true)
        wipeGameBoard()
    }

    fun newGame() {
        _player1.value = null
        _player2.value = null
        _updateCell.value = UpdateCell(0, 0, false, true)
        wipeGameBoard()
    }

    fun menuNavigate(type: NavigationType) {
        when (type) {
            NavigationType.HISTORY -> {
                val scores = Scores(_scoreList)
                _navigateAction.value = MenuFragmentDirections
                    .actionMenuFragmentToScoreFragment(
                        scores, _player1.value ?: Player(""),
                        _player2.value ?: Player("")
                    )
            }
            NavigationType.NEW -> {
                newGame()
                _navigateAction.value = MenuFragmentDirections.actionMenuFragmentToUserFragment()
            }
            NavigationType.ABOUT -> {
                _navigateAction.value = MenuFragmentDirections.actionMenuFragmentToAboutFragment()
            }
        }
    }

    fun clearNavigation() {
        _navigateAction.value = null
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}
