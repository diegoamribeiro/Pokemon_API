package com.dmribeiro.pokedex_app.repository

import android.util.Log
import com.dmribeiro.pokedex_app.di.local.LocalDataSource
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.toDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

interface Repository {
    suspend fun getListPokemon(): List<Pokemon>
}

@ActivityRetainedScoped
class RepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val localDataSource: LocalDataSource
) : Repository {


    override suspend fun getListPokemon(): List<Pokemon> {
        val listOfPokemon = arrayListOf<Pokemon>()
        val response = remoteDatasource.getAllPokemon()
        try {
            if (response.isSuccessful) {
                val list = response.body()?.pokemonResult
                list.let { pokemon ->
                    pokemon?.map {
                        listOfPokemon.addAll(listOf(it))
                    }
                }
            } else {
                Log.d("*Response", response.errorBody().toString())
            }
        } catch (exception: Exception) {
            throw exception
        }
        return listOfPokemon
    }
}