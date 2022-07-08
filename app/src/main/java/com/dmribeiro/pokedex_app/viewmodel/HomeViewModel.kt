package com.dmribeiro.pokedex_app.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.remote.NetworkResponse
import com.dmribeiro.pokedex_app.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    val pokemonResponse: MutableLiveData<NetworkResponse<List<Pokemon>>> = MutableLiveData()

    fun getAllPokemon() = viewModelScope.launch(Dispatchers.IO) {
        getAllSafePokemon()
    }

    private suspend fun getAllSafePokemon(){
        pokemonResponse.postValue(NetworkResponse.Loading())
        val response = repository.remote.getAllPokemon()
        val pokemon = getListPokemon(response)
        pokemonResponse.postValue(pokemon)
    }

    fun getAllLocalPokemon(){
        repository.local.getAllPokemon()
    }

    private suspend fun getListPokemon(response: Response<PokemonResponse>): NetworkResponse<List<Pokemon>> {
        pokemonResponse.postValue(NetworkResponse.Loading())
        val listOfPokemon = arrayListOf<Pokemon>()
        return if (response.isSuccessful){
            val pokemonResponse = handleSafeAllPokemon(response)
            pokemonResponse.data.let {
                val p = pokemonResponse.data!!.pokemonResult
                p.map { listPokemon ->
                    val listItem = repository.remote.getPokemon(listPokemon.name)
                    listOfPokemon.addAll(listOf(listItem))
                    repository.local.insertPokemon(listItem)
                }
            }
                NetworkResponse.Success(listOfPokemon)
        }else{
            NetworkResponse.Error(response.message())
        }
    }

    private fun handleSafeAllPokemon(response: Response<PokemonResponse>): NetworkResponse<PokemonResponse>{
        pokemonResponse.postValue(NetworkResponse.Loading())
        return if (response.isSuccessful){
            val pokemonResponses = response.body()
            NetworkResponse.Success(pokemonResponses!!)
        }else{
            NetworkResponse.Error(response.message())
        }
    }

//    val pokemonApiResult = repository.remote.getPokemon(number)

//    private suspend fun getAllSafePokemon() {
//        //pokemon.postValue(NetworkResponse.Loading())
//        val response = repository.remote.getAllPokemon()
//        val pokemons = response.body()
//        val list = arrayListOf<Pokemon>()
//        for (p in pokemons!!.pokemonResult){
//            val one = getOnePokemon(p.name)
//            list.add(one)
//        }
//        pokemon.postValue(list)
//    }
//
//    private suspend fun getOnePokemon(name: String) : Pokemon{
//        val response = repository.remote.getPokemon(name)
//        return response.body()!!
//    }


}