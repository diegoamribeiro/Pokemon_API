package com.dmribeiro.pokedex_app.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmribeiro.pokedex_app.data.local.model.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM POKEMON_TABLE ORDER BY number ASC")
    fun getAllLocalPokemon(): List<PokemonEntity>

    @Query("DELETE FROM POKEMON_TABLE")
    fun deleteAllLocalPokemon()

    @Query("SELECT * FROM POKEMON_TABLE WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<PokemonEntity>>

}
