package com.dmribeiro.pokedex_app.data.remote.model


import com.dmribeiro.pokedex_app.domain.model.*
import com.google.gson.annotations.SerializedName

data class CompletePokemonResponse(
    @SerializedName("abilities")
    val abilities: List<AbilitiesResponse>?,
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
    val stats: List<StatsResponse>?,
    @SerializedName("types")
    val types: List<TypesResponse>,
    @SerializedName("sprites")
    val spritesResponse: SpritesResponse?,
    @SerializedName("species")
    val speciesResponse: SpeciesResponse
)

fun CompletePokemonResponse.toDomain(): Pokemon {
    return Pokemon(
        abilities = this.abilities?.map {
            Abilities(
                slot = it.slot,
                name = it.abilityResponse.name,
                url = it.abilityResponse.url
            )
        },
        baseExperience = this.baseExperience,
        weight = this.weight,
        height = this.height,
        number = this.id,
        name = this.name,
        stats = this.stats?.map {
            Stats(
                baseStat = it.baseStat,
                effort = it.effort
            )
        },
        types = this.types.map {
            Types(
                slot = it.slot,
                name = it.type.name
            )
        },
        imageUrl = this.spritesResponse?.other?.officialArt?.frontDefault,
        speciesResponse = Species(
            name = this.speciesResponse.name,
            url = this.speciesResponse.url
        )
    )
}

