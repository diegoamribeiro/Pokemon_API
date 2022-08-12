package com.dmribeiro.pokedex_app.di.local

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

    suspend fun deletePokemon(){
        pokemonDao.deleteAllLocalPokemon()
    }

}