package com.dmribeiro.pokedex_app.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.dmribeiro.pokedex_app.di.local.LocalDataSource
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.evolution.EvolutionChain
import com.dmribeiro.pokedex_app.model.toDomain
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

    private suspend fun getRemotePokemon(): List<Pokemon> {
        val listOfPokemon: List<Pokemon> = remoteDatasource.getAllPokemon().body()!!.pokemonResult.map {
            getPokemon(it.name)
        }

        val localData = localDataSource.getAllLocalPokemon()
        if (listOfPokemon.size != localData.size){
            localDataSource.deletePokemon()
            localDataSource.insertPokemon(listOfPokemon)
        }

        return localDataSource.getAllLocalPokemon()
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

    override fun searchPokemon(pokemon: String): LiveData<List<Pokemon>> {
        return localDataSource.searchPokemon(pokemon)
    }

    override suspend fun getEvolutionChain(name: String) : EvolutionChain {
        var itNumber: String? = null
        remoteDatasource.getAllPokemon().body()!!.pokemonResult.map {
            itNumber = getPokemon(name).species!!.url
        }
        val number = itNumber!!.replace("https://pokeapi.co/api/v2/pokemon-species/", "")

        val lastCharacter = number.replace("/", "")
        Log.d("***EvolutionNumber", lastCharacter)
        val data = remoteDatasource.getEvolutionChain(lastCharacter)
        var evolutionChain: EvolutionChain? = null
        if (data.isSuccessful){
            evolutionChain = data.body()!!.chainEntity
        }
        return evolutionChain!!
    }

}