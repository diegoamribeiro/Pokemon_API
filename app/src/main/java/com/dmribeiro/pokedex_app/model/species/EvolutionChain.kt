package com.dmribeiro.pokedex_app.model.species


import com.google.gson.annotations.SerializedName

data class EvolutionChain(
    @SerializedName("url")
    val url: String
)