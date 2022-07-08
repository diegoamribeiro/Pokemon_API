package com.dmribeiro.pokedex_app.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.dmribeiro.pokedex_app.domain.Pokemon


@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(PokemonConverter::class)
abstract class PokemonDatabase {
    abstract fun pokemonDao(): PokemonDao
}