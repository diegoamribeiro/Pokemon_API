package com.dmribeiro.pokedex_app.repository

import com.dmribeiro.pokedex_app.model.PokemonEntity
import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.model.evolution.EvolutionResponse
import com.dmribeiro.pokedex_app.remote.PokemonApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val pokemonApiService: PokemonApiService
){
    suspend fun getAllPokemon(): Response<PokemonResponse>{
        return pokemonApiService.getAllPokemon()
    }

    suspend fun getPokemon(name: String): Response<PokemonEntity>{
        return pokemonApiService.getPokemon(name)
    }

    suspend fun getEvolutionChain(number: String): Response<EvolutionResponse>{
        return pokemonApiService.getEvolutionChain(number)
    }

}
