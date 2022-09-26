package com.dmribeiro.pokedex_app.domain.usecase

import android.os.RemoteException
import android.util.Log
import com.dmribeiro.pokedex_app.domain.repository.Repository
import com.dmribeiro.pokedex_app.domain.model.Pokemon
import javax.inject.Inject

class GetAllPokemonUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun  invoke() : Result<List<Pokemon>> {
        return try {
            Log.d("*LoadData", repository.getAllPokemon().toString())
            Result.success(repository.getAllPokemon())
        }catch (exception: RemoteException){
            Result.failure(exception)
        }
    }

}