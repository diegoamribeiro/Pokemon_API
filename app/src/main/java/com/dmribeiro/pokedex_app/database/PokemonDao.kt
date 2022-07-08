package com.dmribeiro.pokedex_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmribeiro.pokedex_app.domain.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("SELECT * FROM POKEMON_TABLE ORDER BY name ASC")
    fun getAllPokemon(): Flow<List<Pokemon>>

}
