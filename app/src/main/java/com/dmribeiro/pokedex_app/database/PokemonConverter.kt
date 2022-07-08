package com.dmribeiro.pokedex_app.database

import androidx.room.TypeConverter
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.Abilities
import com.dmribeiro.pokedex_app.model.Ability
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPokemonToString(pokemon: Pokemon): String{
        return gson.toJson(pokemon)
    }

    @TypeConverter
    fun fromStringToPokemon(pokemon: String): Pokemon{
        val listType = object: TypeToken<Pokemon>(){}.type
        return gson.fromJson(pokemon, listType)
    }

    @TypeConverter
    fun fromAbilityToString(ability: Ability): String{
        return gson.toJson(ability)
    }

    @TypeConverter
    fun fromStringToAbility(ability: String): Ability{
        val listType = object: TypeToken<Ability>(){}.type
        return gson.fromJson(ability, listType)
    }

    @TypeConverter
    fun fromAbilitiesToString(abilities: Abilities): String{
        return gson.toJson(abilities)
    }

    @TypeConverter
    fun fromStringToAbilities(abilities: String): Abilities{
        val listType = object: TypeToken<Abilities>(){}.type
        return gson.fromJson(abilities, listType)
    }

}