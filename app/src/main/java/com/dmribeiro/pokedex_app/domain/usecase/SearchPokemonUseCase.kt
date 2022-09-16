package com.dmribeiro.pokedex_app.domain.usecase

import androidx.lifecycle.LiveData
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.repository.Repository
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val repository: Repository
) {

    fun searchPokemonFromDatabase(search: String) : LiveData<List<Pokemon>>{
        return try {
            repository.searchPokemon(search)
        }catch (exception: Exception){
            throw  exception
        }
    }

}