package com.dmribeiro.pokedex_app.repository

import com.dmribeiro.pokedex_app.domain.Pokemon

interface Repository {
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun getPokemon(name: String): Pokemon
    suspend fun insertPokemon(pokemon: List<Pokemon>)
}