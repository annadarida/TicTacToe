package com.example.domains.usecases.base

import io.reactivex.rxjava3.core.Observable

interface SimpleUseCase<in Input> {

    /**
     * Builds the use case with the provided input and returns an Observable.
     *
     * @param input the optional input parameter
     * @return Observable<Unit> a stream of events (can be used for any purpose)
     */
    fun buildUseCase(input: Input? = null): Observable<Unit>
}