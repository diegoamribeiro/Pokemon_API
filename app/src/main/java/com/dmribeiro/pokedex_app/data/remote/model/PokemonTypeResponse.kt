package com.dmribeiro.pokedex_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class PokemonTypeResponse(
    @SerializedName("name")
    val name: String
    )