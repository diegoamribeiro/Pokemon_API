package com.dmribeiro.pokedex_app.data.repository

import androidx.lifecycle.LiveData
import com.dmribeiro.pokedex_app.data.remote.model.CompletePokemonResponse
import com.dmribeiro.pokedex_app.domain.model.Pokemon

interface Repository {
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun getPokemon(name: String): CompletePokemonResponse
    suspend fun insertPokemon(pokemon: List<Pokemon>)
    fun searchPokemon(pokemon: String) : LiveData<List<Pokemon>>
}