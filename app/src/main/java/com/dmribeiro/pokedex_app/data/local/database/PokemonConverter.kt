package com.dmribeiro.pokedex_app.data.local.database

import androidx.room.TypeConverter
import com.dmribeiro.pokedex_app.data.local.model.*
import com.dmribeiro.pokedex_app.data.remote.model.*
import com.dmribeiro.pokedex_app.data.remote.model.PokemonTypeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPokemonToString(pokemon: PokemonEntity): String {
        return gson.toJson(pokemon)
    }

    @TypeConverter
    fun fromStringToPokemon(pokemon: String): PokemonEntity {
        val listType = object : TypeToken<PokemonEntity>() {}.type
        return gson.fromJson(pokemon, listType)
    }

    @TypeConverter
    fun fromAbilityToString(abilityEntity: AbilitiesEntity): String {
        return gson.toJson(abilityEntity)
    }

    @TypeConverter
    fun fromStringToAbility(ability: String): AbilitiesEntity {
        val listType = object : TypeToken<AbilityResponse>() {}.type
        return gson.fromJson(ability, listType)
    }

    @TypeConverter
    fun fromAbilitiesToString(abilities: List<AbilitiesEntity>): String {
        return gson.toJson(abilities)
    }

    @TypeConverter
    fun fromStringToAbilities(abilities: String): List<AbilitiesEntity> {
        val listType = object : TypeToken<List<AbilitiesEntity>>() {}.type
        return gson.fromJson(abilities, listType)
    }

    @TypeConverter
    fun fromTypesToString(types: List<TypesEntity>): String {
        return gson.toJson(types)
    }

    @TypeConverter
    fun fromStringToTypes(types: String): List<TypesEntity> {
        val listType = object : TypeToken<List<TypesEntity>>() {}.type
        return gson.fromJson(types, listType)
    }

    @TypeConverter
    fun fromStatsToListString(stats: List<StatsEntity>): String {
        return gson.toJson(stats)
    }

    @TypeConverter
    fun fromStringToListOfStats(stats: String): List<StatsEntity> {
        val listType = object : TypeToken<List<StatsEntity>>() {}.type
        return gson.fromJson(stats, listType)
    }

    @TypeConverter
    fun fromStatsToString(statsEntity: StatsEntity): String {
        return gson.toJson(statsEntity)
    }

    @TypeConverter
    fun fromStringToStats(stats: String): StatsEntity {
        val listType = object : TypeToken<StatsResponse>() {}.type
        return gson.fromJson(stats, listType)
    }

    @TypeConverter
    fun fromPokemonTypeToString(abilities: PokemonTypeResponse): String {
        return gson.toJson(abilities)
    }

    @TypeConverter
    fun fromStringToPokemonType(abilities: String): PokemonTypeResponse {
        val listType = object : TypeToken<PokemonTypeResponse>() {}.type
        return gson.fromJson(abilities, listType)
    }

    @TypeConverter
    fun fromSpriteToString(spritesResponse: SpritesResponse): String {
        return gson.toJson(spritesResponse)
    }

    @TypeConverter
    fun fromStringToSprite(sprites: String): SpritesResponse {
        val listType = object : TypeToken<SpritesResponse>() {}.type
        return gson.fromJson(sprites, listType)
    }

    @TypeConverter
    fun fromSpeciesToString(speciesEntity: SpeciesEntity): String {
        return gson.toJson(speciesEntity)
    }

    @TypeConverter
    fun fromStringToSpecies(species: String): SpeciesEntity {
        val listType = object : TypeToken<SpeciesEntity>() {}.type
        return gson.fromJson(species, listType)
    }

}