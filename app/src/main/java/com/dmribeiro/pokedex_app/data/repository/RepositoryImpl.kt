package com.dmribeiro.pokedex_app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dmribeiro.pokedex_app.data.local.datasource.LocalDataSource
import com.dmribeiro.pokedex_app.data.local.model.toDomain
import com.dmribeiro.pokedex_app.data.local.model.toEntity
import com.dmribeiro.pokedex_app.data.remote.datasource.RemoteDatasource
import com.dmribeiro.pokedex_app.data.remote.model.CompletePokemonResponse
import com.dmribeiro.pokedex_app.data.remote.model.toDomain
import com.dmribeiro.pokedex_app.domain.model.Pokemon
import com.dmribeiro.pokedex_app.domain.repository.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val localDataSource: LocalDataSource
) : Repository {


    override suspend fun getAllPokemon(): List<Pokemon> {
        return if (localDataSource.getAllLocalPokemon().isNullOrEmpty()){
            getRemotePokemon()
        }else{
            localDataSource.getAllLocalPokemon().map { it.toDomain() }
        }
    }

    private suspend fun getRemotePokemon(): List<Pokemon> {
        val listOfPokemon: List<Pokemon> = remoteDatasource.getAllPokemon().body()!!.pokemonResponse.map {
            getPokemon(it.name).toDomain()
        }

        val localData = localDataSource.getAllLocalPokemon()
        if (listOfPokemon.size != localData.size){
            localDataSource.deletePokemon()
            localDataSource.insertPokemon(listOfPokemon.map { it.toEntity() })
        }

        return localDataSource.getAllLocalPokemon().map { it.toDomain() }
    }

    override suspend fun getPokemon(name: String): CompletePokemonResponse {
        val response = remoteDatasource.getPokemon(name)
        var pokemon: CompletePokemonResponse? = null
        try {
            if (response.isSuccessful){
                pokemon = response.body()
            }
        }catch (exception: Exception){
            throw exception
        }
        return pokemon!!
    }

    override suspend fun insertPokemon(pokemon: List<Pokemon>) {
        localDataSource.insertPokemon(pokemon.map { it.toEntity() })
    }

    override fun searchPokemon(pokemon: String): LiveData<List<Pokemon>> {
        return localDataSource.searchPokemon(pokemon).map { list -> list.map { it.toDomain() } }
    }

}