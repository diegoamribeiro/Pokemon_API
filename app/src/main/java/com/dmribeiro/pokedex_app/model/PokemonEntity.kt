package com.dmribeiro.pokedex_app.model


import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.utils.Constants
import com.dmribeiro.pokedex_app.utils.Constants.IMAGE_URL
import com.google.gson.annotations.SerializedName

data class PokemonEntity(
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
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("species")
    val species: Species
)

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        abilities = this.abilities,
        baseExperience = this.baseExperience,
        weight = this.weight,
        height = this.height,
        number = this.id,
        name = this.name,
        stats = this.stats,
        types = this.types!!,
        imageUrl = this.sprites,
        species = this.species
    )
}

