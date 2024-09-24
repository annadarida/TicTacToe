package com.example.domains.usecases.base

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class ObservableUseCase<T : Any, in Input> {

    private val subject = BehaviorSubject.create<T>()

    /**
     * Abstract method to generate Observable for the use case.
     */
    protected abstract fun generateObservable(input: Input): Observable<T>

    /**
     * Builds the use case by creating the observable and applying schedulers.
     */
    fun buildUseCase(input: Input): Observable<T> {
        return generateObservable(input)
            .subscribeOn(Schedulers.io()) // Run the observable on IO thread
            .doOnNext { subject.onNext(it) } // Pass emitted data to BehaviorSubject
    }

    /**
     * Exposes BehaviorSubject to allow subscription to its data.
     */
    fun observe(): Observable<T> {
        return subject.hide().observeOn(Schedulers.computation()) // Read from BehaviorSubject, ensuring it can’t be modified outside.
    }
}
