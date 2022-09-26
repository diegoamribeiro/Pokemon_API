package com.dmribeiro.pokedex_app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dmribeiro.pokedex_app.data.local.model.PokemonEntity


@Database(entities = [PokemonEntity::class], version = 2, exportSchema = false)
@TypeConverters(PokemonConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}