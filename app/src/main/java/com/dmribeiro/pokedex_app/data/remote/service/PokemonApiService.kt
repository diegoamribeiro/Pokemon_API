package com.dmribeiro.pokedex_app.data.remote.service

import com.dmribeiro.pokedex_app.data.remote.model.CompletePokemonResponse
import com.dmribeiro.pokedex_app.data.remote.model.AllPokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon?limit=151")
    suspend fun getAllPokemon(): Response<AllPokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Response<CompletePokemonResponse>

}