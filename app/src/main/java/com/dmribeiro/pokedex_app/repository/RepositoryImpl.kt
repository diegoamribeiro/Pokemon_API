package com.dmribeiro.pokedex_app.repository

import android.util.Log
import com.dmribeiro.pokedex_app.di.local.LocalDataSource
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.toDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val localDataSource: LocalDataSource
) : Repository {


    override suspend fun getAllPokemon(): List<Pokemon> {
        return if (localDataSource.getAllLocalPokemon().isNullOrEmpty()){
            getRemotePokemon()
        }else{
            localDataSource.getAllLocalPokemon()
        }
    }

    private suspend fun getRemotePokemon(): List<Pokemon>{
        val listOfPokemon: List<Pokemon> = remoteDatasource.getAllPokemon().body()!!.pokemonResult.map {
            getPokemon(it.name)
        }

        if (listOfPokemon != localDataSource.getAllLocalPokemon()){
            localDataSource.deletePokemon()
            localDataSource.insertPokemon(listOfPokemon)
        }

        return listOfPokemon
    }

    override suspend fun getPokemon(name: String): Pokemon {
        val response = remoteDatasource.getPokemon(name)
        var pokemon: Pokemon? = null
        try {
            if (response.isSuccessful){
                pokemon = response.body()!!.toDomain()
            }
        }catch (exception: Exception){
            throw exception
        }
        return pokemon!!
    }

    override suspend fun insertPokemon(pokemon: List<Pokemon>) {
        localDataSource.insertPokemon(pokemon)
    }
}