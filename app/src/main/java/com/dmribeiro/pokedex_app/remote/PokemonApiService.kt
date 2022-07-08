package com.dmribeiro.pokedex_app.remote

import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.PokemonApiResult
import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.model.PokemonResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon?limit=151")
    suspend fun getAllPokemon(): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon

}