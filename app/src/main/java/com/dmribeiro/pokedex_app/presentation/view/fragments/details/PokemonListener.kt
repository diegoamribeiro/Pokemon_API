package com.dmribeiro.pokedex_app.presentation.view.fragments.details

import com.dmribeiro.pokedex_app.domain.model.Pokemon

interface PokemonListener {
    fun callPokemon(): Pokemon
}