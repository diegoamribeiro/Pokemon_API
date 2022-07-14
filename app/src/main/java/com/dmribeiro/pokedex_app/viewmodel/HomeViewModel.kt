package com.dmribeiro.pokedex_app.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.domain.usecase.GetAllPokemonUseCase
import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.remote.ResponseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase
): ViewModel() {

    private val _pokemonResponse = MutableLiveData<ResponseViewState<List<Pokemon>>>()
    val pokemonResponse: MutableLiveData<ResponseViewState<List<Pokemon>>> = _pokemonResponse

    fun getAllPokemon() = viewModelScope.launch(Dispatchers.IO) {
        getAllPokemonUseCase().onSuccess {
            _pokemonResponse.postValue(ResponseViewState.Success(it))
        }.onFailure {
            _pokemonResponse.postValue(ResponseViewState.Error(it.toString()))
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