package com.dmribeiro.pokedex_app.model

import com.dmribeiro.pokedex_app.domain.PokemonType
import com.google.gson.annotations.SerializedName

data class PokemonTypeSlot(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: PokemonType
)
