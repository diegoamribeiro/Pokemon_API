package com.dmribeiro.pokedex_app.database

import androidx.room.TypeConverter
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.domain.PokemonType
import com.dmribeiro.pokedex_app.model.*
import com.dmribeiro.pokedex_app.model.evolution.EvolutionChain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPokemonToString(pokemon: Pokemon): String {
        return gson.toJson(pokemon)
    }

    @TypeConverter
    fun fromStringToPokemon(pokemon: String): Pokemon {
        val listType = object : TypeToken<Pokemon>() {}.type
        return gson.fromJson(pokemon, listType)
    }

    @TypeConverter
    fun fromAbilityToString(ability: Ability): String {
        return gson.toJson(ability)
    }

    @TypeConverter
    fun fromStringToAbility(ability: String): Ability {
        val listType = object : TypeToken<Ability>() {}.type
        return gson.fromJson(ability, listType)
    }

    @TypeConverter
    fun fromAbilitiesToString(abilities: List<Abilities>): String {
        return gson.toJson(abilities)
    }

    @TypeConverter
    fun fromStringToAbilities(abilities: String): List<Abilities> {
        val listType = object : TypeToken<List<Abilities>>() {}.type
        return gson.fromJson(abilities, listType)
    }

    @TypeConverter
    fun fromTypesToString(types: List<Types>): String {
        return gson.toJson(types)
    }

    @TypeConverter
    fun fromStringToTypes(types: String): List<Types> {
        val listType = object : TypeToken<List<Types>>() {}.type
        return gson.fromJson(types, listType)
    }

    @TypeConverter
    fun fromStatsToListString(stats: List<Stats>): String {
        return gson.toJson(stats)
    }

    @TypeConverter
    fun fromStringToListOfStats(stats: String): List<Stats> {
        val listType = object : TypeToken<List<Stats>>() {}.type
        return gson.fromJson(stats, listType)
    }

    @TypeConverter
    fun fromStatsToString(stats: Stats): String {
        return gson.toJson(stats)
    }

    @TypeConverter
    fun fromStringToStats(stats: String): Stats {
        val listType = object : TypeToken<Stats>() {}.type
        return gson.fromJson(stats, listType)
    }

    @TypeConverter
    fun fromPokemonTypeToString(abilities: PokemonType): String {
        return gson.toJson(abilities)
    }

    @TypeConverter
    fun fromStringToPokemonType(abilities: String): PokemonType {
        val listType = object : TypeToken<PokemonType>() {}.type
        return gson.fromJson(abilities, listType)
    }

    @TypeConverter
    fun fromSpriteToString(sprites: Sprites): String {
        return gson.toJson(sprites)
    }

    @TypeConverter
    fun fromStringToSprite(sprites: String): Sprites {
        val listType = object : TypeToken<Sprites>() {}.type
        return gson.fromJson(sprites, listType)
    }

    @TypeConverter
    fun fromSpeciesToString(species: Species): String {
        return gson.toJson(species)
    }

    @TypeConverter
    fun fromStringToSpecies(species: String): Species {
        val listType = object : TypeToken<Species>() {}.type
        return gson.fromJson(species, listType)
    }

    @TypeConverter
    fun fromEvolutionChainToString(evolutionChain: EvolutionChain): String {
        return gson.toJson(evolutionChain)
    }

    @TypeConverter
    fun fromStringToEvolutionChain(evolutionChain: String): EvolutionChain {
        val listType = object : TypeToken<EvolutionChain>() {}.type
        return gson.fromJson(evolutionChain, listType)
    }

}