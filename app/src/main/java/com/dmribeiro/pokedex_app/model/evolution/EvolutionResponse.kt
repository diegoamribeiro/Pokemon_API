package com.dmribeiro.pokedex_app.model.evolution


import com.google.gson.annotations.SerializedName

data class EvolutionResponse(
    @SerializedName("baby_trigger_item")
    val babyTriggerItem: Any,
    @SerializedName("chain")
    val chainEntity: EvolutionChain,
    @SerializedName("id")
    val id: Int
)