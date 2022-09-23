package com.dmribeiro.pokedex_app.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmribeiro.pokedex_app.domain.model.*
import com.dmribeiro.pokedex_app.utils.Constants.POKEMON_TABLE
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = POKEMON_TABLE)
data class PokemonEntity(

    @PrimaryKey(autoGenerate = false)
    val number: Int,
    val abilities: List<AbilitiesEntity>?,
    val baseExperience: Int?,
    val weight: Int?,
    val height: Int?,
    val name: String,
    val stats: List<StatsEntity>?,
    val types: List<TypesEntity>,
    val imageUrl: String?,
    val speciesResponse: SpeciesEntity?
)

fun PokemonEntity.toDomain() = Pokemon(
    number= this.number,
    abilities = this.abilities?.map { 
        Abilities(
            slot = it.slot,
            name = it.name,
            url = it.url
        )                           
    },
    baseExperience = this.baseExperience,
    weight = this.weight,
    height = this.height,
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
            name = it.name
        )
    },
    imageUrl = this.imageUrl,
    speciesResponse = this.speciesResponse?.let {
        Species(
            name = it.name,
            url = it.url
        )
    }
)

fun Pokemon.toEntity() = PokemonEntity(
    number= this.number,
    abilities = this.abilities?.map {
        AbilitiesEntity(
            slot = it.slot,
            name = it.name,
            url = it.url
        )
    },
    baseExperience = this.baseExperience,
    weight = this.weight,
    height = this.height,
    name = this.name,
    stats = this.stats?.map {
        StatsEntity(
            baseStat = it.baseStat,
            effort = it.effort
        )
    },
    types = this.types.map {
        TypesEntity(
            slot = it.slot,
            name = it.name
        )
    },
    imageUrl = this.imageUrl,
    speciesResponse = this.speciesResponse?.let {
        SpeciesEntity(
            name = it.name,
            url = it.url
        )
    }
)


