package com.dmribeiro.pokedex_app.di.local

import androidx.lifecycle.LiveData
import com.dmribeiro.pokedex_app.database.PokemonDao
import com.dmribeiro.pokedex_app.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor (
    private val pokemonDao: PokemonDao
    ) {

    fun getAllLocalPokemon(): List<Pokemon>{
        return pokemonDao.getAllLocalPokemon()
    }

    suspend fun insertPokemon(pokemon: List<Pokemon>){
        pokemonDao.insertPokemon(pokemon)
    }

    fun deletePokemon(){
        pokemonDao.deleteAllLocalPokemon()
    }

    fun searchPokemon(pokemon: String) : LiveData<List<Pokemon>>{
        return pokemonDao.searchDatabase(pokemon)
    }

}