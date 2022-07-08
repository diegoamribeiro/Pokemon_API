package com.dmribeiro.pokedex_app.view.fragments.details

import com.dmribeiro.pokedex_app.domain.Pokemon

interface PokemonListener {
    fun callPokemon(): Pokemon
}