package com.dmribeiro.pokedex_app.presentation.state

sealed class ResponseViewState<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) : ViewState(){
    class Success<T>(data: T): ResponseViewState<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null): ResponseViewState<T>(data, throwable)
    class Loading<T> : ResponseViewState<T>()
}