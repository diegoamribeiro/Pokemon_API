package com.dmribeiro.pokedex_app.model.evolution


import com.dmribeiro.pokedex_app.model.Species
import com.google.gson.annotations.SerializedName

data class EvolvesTo(
    @SerializedName("evolution_details")
    val evolutionDetails: List<EvolutionDetail>,
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("species")
    val species: Species
)