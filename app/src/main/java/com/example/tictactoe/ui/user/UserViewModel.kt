package com.example.tictactoe.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.domains.usecases.SavePlayersUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserViewModel(
    private val savePlayersUseCase: SavePlayersUseCase
) : ViewModel() {

    private var _navigateState = MutableLiveData<NavDirections>()
    val navigateState: LiveData<NavDirections>
        get() = _navigateState

    private val disposables = CompositeDisposable()

    fun setUpPlayers(name1: String, name2: String) {
        val input = SavePlayersUseCase.Input(name1, name2)
        disposables.add(
            savePlayersUseCase.buildUseCase(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        println("Players saved successfully!")
                        _navigateState.postValue(
                            UserFragmentDirections.actionUserFragmentToGameFragment()
                        )
                    },
                    { error ->
                        println("Error saving players: ${error.message}")
                    }
                )
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
