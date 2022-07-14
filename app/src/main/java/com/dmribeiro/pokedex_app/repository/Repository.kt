package com.dmribeiro.pokedex_app.repository

import com.dmribeiro.pokedex_app.domain.Pokemon

interface Repository {
    suspend fun getListPokemon(): List<Pokemon>
    suspend fun getPokemon(name: String): Pokemon
}