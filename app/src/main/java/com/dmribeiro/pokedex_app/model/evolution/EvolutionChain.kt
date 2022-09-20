package com.dmribeiro.pokedex_app.model.evolution


import com.dmribeiro.pokedex_app.model.Species
import com.google.gson.annotations.SerializedName

data class EvolutionChain(
    @SerializedName("evolution_details")
    val evolutionDetails: List<Any>,
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("species")
    val species: Species
)