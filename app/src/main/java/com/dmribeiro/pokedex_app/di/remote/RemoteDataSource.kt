package com.dmribeiro.pokedex_app.di.remote

import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.remote.PokemonApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val pokemonApiService: PokemonApiService
){

    suspend fun getAllPokemon() : Response<PokemonResponse> {
        return pokemonApiService.getAllPokemon()
    }

}