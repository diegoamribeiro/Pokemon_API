package com.dmribeiro.pokedex_app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dmribeiro.pokedex_app.data.local.datasource.LocalDataSource
import com.dmribeiro.pokedex_app.data.local.model.PokemonEntity
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
        val localPokemon = localDataSource.getAllLocalPokemon()
        return if (localPokemon.isNullOrEmpty()){
            getRemotePokemon()
        } else {
            localPokemon.map { it.toDomain() }
        }
    }

    private suspend fun getRemotePokemon(): List<Pokemon> {
        val response = remoteDatasource.getAllPokemon()

        if (response.isSuccessful.not()) {
            throw PokemonFetchException("Unable to fetch Pokemon from remote source")
        }

        val remotePokemon = response.body()?.pokemonResponse?.map {
            getPokemon(it.name).toDomain()
        } ?: emptyList()

        val localData = localDataSource.getAllLocalPokemon()

        if (!areListsEqual(remotePokemon, localData)) {
            localDataSource.deletePokemon()
            localDataSource.insertPokemon(remotePokemon.map { it.toEntity() })
        }

        return localDataSource.getAllLocalPokemon().map { it.toDomain() }
    }

    private fun areListsEqual(listLocal: List<Pokemon>, listRemote: List<PokemonEntity>): Boolean {
        return listLocal.size == listRemote.size && listLocal.all { listRemote.contains(it.toEntity()) }
    }

    override suspend fun getPokemon(name: String): CompletePokemonResponse {
        val response = remoteDatasource.getPokemon(name)

        if (response.isSuccessful.not()) {
            throw PokemonFetchException("Unable to fetch Pokemon details from remote source")
        }

        return response.body() ?: throw PokemonFetchException("Received null Pokemon details from remote source")
    }

    override suspend fun insertPokemon(pokemon: List<Pokemon>) {
        localDataSource.insertPokemon(pokemon.map { it.toEntity() })
    }

    override fun searchPokemon(pokemon: String): LiveData<List<Pokemon>> {
        return localDataSource.searchPokemon(pokemon).map { list -> list.map { it.toDomain() } }
    }
}

class PokemonFetchException(message: String): Exception(message)

