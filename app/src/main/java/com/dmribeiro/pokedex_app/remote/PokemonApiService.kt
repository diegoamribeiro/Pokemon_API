package com.dmribeiro.pokedex_app.remote

import com.dmribeiro.pokedex_app.model.PokemonEntity
import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.model.evolution.EvolutionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon?limit=151")
    suspend fun getAllPokemon(): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Response<PokemonEntity>

    @GET("evolution-chain/{number}")
    suspend fun getEvolutionChain(@Path("number") number: String): Response<EvolutionResponse>

}