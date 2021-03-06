package com.dmribeiro.pokedex_app.di.local

import com.dmribeiro.pokedex_app.database.PokemonDao
import com.dmribeiro.pokedex_app.database.PokemonDatabase
import com.dmribeiro.pokedex_app.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.Constructor
import javax.inject.Inject

class LocalDataSource @Inject constructor (private val pokemonDao: PokemonDao) {

    fun getAllPokemon(): Flow<List<Pokemon>>{
        return pokemonDao.getAllPokemon()
    }

    suspend fun insertPokemon(pokemon: Pokemon){
        pokemonDao.insertPokemon(pokemon)
    }

}