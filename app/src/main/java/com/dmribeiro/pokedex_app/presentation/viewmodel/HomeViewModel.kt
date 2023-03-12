package com.dmribeiro.pokedex_app.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro.pokedex_app.domain.model.Pokemon
import com.dmribeiro.pokedex_app.domain.usecase.GetAllPokemonUseCase
import com.dmribeiro.pokedex_app.domain.usecase.SearchPokemonUseCase
import com.dmribeiro.pokedex_app.presentation.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
) : ViewModel() {

    private var _pokemonResponse = MutableLiveData<Resource<List<Pokemon>>>()
    val pokemonResponse: LiveData<Resource<List<Pokemon>>> = _pokemonResponse

    private val _searchPokemon = MutableLiveData<LiveData<List<Pokemon>>>()
    val searchPokemon: LiveData<LiveData<List<Pokemon>>> = _searchPokemon

    fun getAllPokemon() = viewModelScope.launch(Dispatchers.IO) {
        _pokemonResponse.postValue(Resource.Loading())
        getAllPokemonUseCase().onSuccess {
            _pokemonResponse.postValue(Resource.Success(it))
        }.onFailure {
            _pokemonResponse.postValue(Resource.Error(it))
        }
    }

    fun searchPokemon(pokemon: String) : LiveData<List<Pokemon>> {
            return searchPokemonUseCase.searchPokemonFromDatabase(pokemon)
    }
}