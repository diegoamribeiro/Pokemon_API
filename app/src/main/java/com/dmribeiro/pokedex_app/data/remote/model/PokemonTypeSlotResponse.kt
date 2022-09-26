package com.dmribeiro.pokedex_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class PokemonTypeSlotResponse(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: PokemonTypeResponse
)
