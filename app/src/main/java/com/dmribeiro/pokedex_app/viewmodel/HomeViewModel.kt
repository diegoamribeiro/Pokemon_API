package com.dmribeiro.pokedex_app.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.domain.usecase.GetAllPokemonUseCase
import com.dmribeiro.pokedex_app.domain.usecase.GetEvolutionChainUseCase
import com.dmribeiro.pokedex_app.domain.usecase.SearchPokemonUseCase
import com.dmribeiro.pokedex_app.model.PokemonResponse
import com.dmribeiro.pokedex_app.model.evolution.EvolutionChain
import com.dmribeiro.pokedex_app.remote.ResponseViewState
import com.dmribeiro.pokedex_app.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val getEvolutionChainUseCase: GetEvolutionChainUseCase
) : ViewModel() {

    private val _pokemonResponse = MutableLiveData<ResponseViewState<List<Pokemon>>>()
    val pokemonResponse: LiveData<ResponseViewState<List<Pokemon>>> = _pokemonResponse

    private val _evolutionChain = MutableLiveData<ResponseViewState<LiveData<EvolutionChain>>>()
    val evolutionChain: LiveData<ResponseViewState<LiveData<EvolutionChain>>> = _evolutionChain

    fun getAllPokemon() = viewModelScope.launch(Dispatchers.IO) {
        _pokemonResponse.postValue(ResponseViewState.Loading())
        getAllPokemonUseCase().onSuccess {
            _pokemonResponse.postValue(ResponseViewState.Success(it))
        }.onFailure {
            _pokemonResponse.postValue(ResponseViewState.Error(it))
        }
    }

    fun searchPokemon(pokemon: String) : LiveData<List<Pokemon>> {
            return searchPokemonUseCase.searchPokemonFromDatabase(pokemon)
    }

    fun getEvolutionChain(name: String) = viewModelScope.launch(Dispatchers.IO) {
        _evolutionChain.postValue(ResponseViewState.Loading())
        getEvolutionChainUseCase.getEvolutionChainUseCase(name).onSuccess {

        }
    }


}