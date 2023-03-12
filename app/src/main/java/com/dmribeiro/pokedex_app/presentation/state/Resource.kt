package com.dmribeiro.pokedex_app.presentation.state

sealed class Resource<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) : ViewState(){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null): Resource<T>(data, throwable)
    class Loading<T> : Resource<T>()
}