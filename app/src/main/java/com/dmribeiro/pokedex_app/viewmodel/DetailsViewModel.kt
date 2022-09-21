package com.dmribeiro.pokedex_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.domain.usecase.GetEvolutionChainUseCase
import com.dmribeiro.pokedex_app.remote.ResponseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getEvolutionChainUseCase: GetEvolutionChainUseCase
): ViewModel() {

    private val _evolutionChain = MutableLiveData<ResponseViewState<List<Pokemon>>>()
    val evolutionChain: LiveData<ResponseViewState<List<Pokemon>>> = _evolutionChain

    fun getEvolutionChain(name: String) = viewModelScope.launch(Dispatchers.IO) {
        _evolutionChain.postValue(ResponseViewState.Loading())
        getEvolutionChainUseCase.getEvolutionChainUseCase(name).onSuccess {
            Log.d("***EvolutionViewModel", it.toString())
            _evolutionChain.postValue(ResponseViewState.Success(it))
        }.onFailure {
            _evolutionChain.postValue(ResponseViewState.Error(it))
        }
    }
}