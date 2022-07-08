package com.dmribeiro.pokedex_app.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmribeiro.pokedex_app.R
import com.dmribeiro.pokedex_app.databinding.FragmentStatsBinding
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.view.fragments.details.PokemonListener


class StatsFragment(val currentPokemon: PokemonListener) : Fragment() {

    private var pokemon: Pokemon? = null
    private lateinit var statsBinding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        statsBinding  = FragmentStatsBinding.inflate(layoutInflater)
        pokemon = currentPokemon.callPokemon()
        Log.d("***Stats", "$$$ - $pokemon")
        return inflater.inflate(R.layout.fragment_stats, container, false)

    }

}