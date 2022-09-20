package com.dmribeiro.pokedex_app.domain.usecase

import androidx.lifecycle.LiveData
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.evolution.EvolutionChain
import com.dmribeiro.pokedex_app.repository.Repository
import javax.inject.Inject

class GetEvolutionChainUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun getEvolutionChainUseCase(name: String) : Result<EvolutionChain>{
        return try {
            Result.success(repository.getEvolutionChain(name))
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }
}