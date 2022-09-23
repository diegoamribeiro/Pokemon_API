package com.dmribeiro.pokedex_app.data.remote.datasource

import com.dmribeiro.pokedex_app.data.remote.model.CompletePokemonResponse
import com.dmribeiro.pokedex_app.data.remote.model.AllPokemonResponse
import com.dmribeiro.pokedex_app.data.remote.service.PokemonApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val pokemonApiService: PokemonApiService
){
    suspend fun getAllPokemon(): Response<AllPokemonResponse>{
        return pokemonApiService.getAllPokemon()
    }

    suspend fun getPokemon(name: String): Response<CompletePokemonResponse>{
        return pokemonApiService.getPokemon(name)
    }

}
