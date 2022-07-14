package com.dmribeiro.pokedex_app.domain.usecase

import android.os.RemoteException
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.repository.Repository
import javax.inject.Inject

class GetAllPokemonUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun  invoke() : Result<List<Pokemon>> {
        return try {
            Result.success(repository.getListPokemon())
        }catch (exception: RemoteException){
            Result.failure(exception)
        }
    }

}