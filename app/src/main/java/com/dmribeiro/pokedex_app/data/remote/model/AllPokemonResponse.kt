package com.dmribeiro.pokedex_app.data.remote.model


import com.google.gson.annotations.SerializedName

data class AllPokemonResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val pokemonResponse: List<PokemonResponse>
)