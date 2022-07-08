package com.dmribeiro.pokedex_app.domain

import com.google.gson.annotations.SerializedName

data class PokemonType(
    @SerializedName("name")
    val name: String
    )