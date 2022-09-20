package com.dmribeiro.pokedex_app.repository

import androidx.lifecycle.LiveData
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.evolution.EvolutionChain

interface Repository {
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun getPokemon(name: String): Pokemon
    suspend fun insertPokemon(pokemon: List<Pokemon>)
    fun searchPokemon(pokemon: String) : LiveData<List<Pokemon>>
    suspend fun getEvolutionChain(name: String) : EvolutionChain
}