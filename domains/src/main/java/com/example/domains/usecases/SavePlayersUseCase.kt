package com.example.domains.usecases

import com.example.domains.entity.Player
import com.example.domains.repository.PlayerRepository
import com.example.domains.usecases.base.SimpleUseCase
import io.reactivex.rxjava3.core.Observable
import kotlin.random.Random

class SavePlayersUseCase(
    val repository: PlayerRepository
) : SimpleUseCase<SavePlayersUseCase.Input> {

    override fun buildUseCase(input: Input?): Observable<Unit> {
        return Observable.create { emitter ->
            // Generate random boolean for player X determination
            val randomBoolean = (Random.nextInt(0, 100) % 2 == 0)

            val player1 = Player(
                name = input?.player1Name ?: "",
                isX = randomBoolean
            )
            val player2 = Player(
                name = input?.player2Name ?: "",
                isX = !randomBoolean
            )

            repository.savePlayers(player1, player2)

            emitter.onNext(Unit)
            emitter.onComplete()
        }
    }

    data class Input(
        val player1Name: String,
        val player2Name: String
    )
}