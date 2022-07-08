package com.dmribeiro.pokedex_app.model


import com.google.gson.annotations.SerializedName

data class PokeResult(
    @SerializedName("abilities")
    val abilities: List<Abilities>?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("stats")
    val stats: List<Stats>?,
    @SerializedName("types")
    val types: List<Types>?,
    val imageUrl: String
)