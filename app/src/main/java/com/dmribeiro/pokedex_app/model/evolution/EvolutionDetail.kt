package com.dmribeiro.pokedex_app.model.evolution


import com.google.gson.annotations.SerializedName

data class EvolutionDetail(
    @SerializedName("min_level")
    val minLevel: Int,
    @SerializedName("trigger")
    val trigger: Trigger,
)