package com.dmribeiro.pokedex_app.repository

import android.util.Log
import com.dmribeiro.pokedex_app.di.local.LocalDataSource
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.toDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val localDataSource: LocalDataSource
) : Repository {


    override suspend fun getListPokemon(): List<Pokemon> {
        val listOfPokemon = arrayListOf<Pokemon>()
        val response = remoteDatasource.getAllPokemon()
        try {
            if (response.isSuccessful) {
                val list = response.body()
                list?.pokemonResult?.map { data ->
                    val pokemon = getPokemon(data.name)
                    listOfPokemon.addAll(listOf(pokemon))
                }
            } else {
                Log.d("*Response", response.errorBody().toString())
            }
        } catch (exception: Exception) {
            throw exception
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
}