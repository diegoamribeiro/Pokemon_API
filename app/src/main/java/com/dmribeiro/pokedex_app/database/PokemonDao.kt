package com.dmribeiro.pokedex_app.database

import androidx.room.*
import com.dmribeiro.pokedex_app.domain.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<Pokemon>)

    @Query("SELECT * FROM POKEMON_TABLE ORDER BY number ASC")
    fun getAllLocalPokemon(): List<Pokemon>

    @Query("DELETE FROM POKEMON_TABLE")
    fun deleteAllLocalPokemon()

}
