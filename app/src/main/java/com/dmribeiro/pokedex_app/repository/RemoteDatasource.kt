package com.dmribeiro.pokedex_app.repository

import com.dmribeiro.pokedex_app.database.PokemonDatabase
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.PokemonApiResult
import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.model.PokemonResult
import com.dmribeiro.pokedex_app.remote.PokemonApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val pokemonApiService: PokemonApiService
){
    suspend fun getAllPokemon(): Response<PokemonResponse>{
        return pokemonApiService.getAllPokemon()
    }

    suspend fun getPokemon(name: String): Pokemon{
        return pokemonApiService.getPokemon(name)
    }

}
