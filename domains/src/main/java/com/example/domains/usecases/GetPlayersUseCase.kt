package com.example.domains.usecases

import com.example.domains.entity.Player
import com.example.domains.repository.PlayerRepository
import com.example.domains.usecases.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable

class GetPlayersUseCase(
    val repository: PlayerRepository
): ObservableUseCase<Pair<Player, Player>, Any?>() {

    override fun generateObservable(input: Any?): Observable<Pair<Player, Player>> {
        return Observable.create { emitter ->
            val playerPair = repository.getPlayers()
            emitter.onNext(playerPair)
            emitter.onComplete()
        }
    }
}