package com.dmribeiro.pokedex_app.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dmribeiro.pokedex_app.R
import com.dmribeiro.pokedex_app.databinding.FragmentStatsBinding
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.model.Stats
import com.dmribeiro.pokedex_app.utils.setTypeBackground
import com.dmribeiro.pokedex_app.utils.setTypeBackgroundDark
import com.dmribeiro.pokedex_app.utils.setTypeBackgroundTint
import com.dmribeiro.pokedex_app.view.fragments.details.PokemonListener


class StatsFragment(val currentPokemon: PokemonListener) : Fragment() {

    private var pokemon: Pokemon? = null
    private lateinit var statsBinding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        statsBinding = FragmentStatsBinding.inflate(layoutInflater)
        pokemon = currentPokemon.callPokemon()
        Log.d("***Stats", "$$$ - $pokemon")
        setStatsColors(pokemon!!.types[0].type.name)
        setProgressValues(pokemon?.stats!!)

        return statsBinding.root
    }

    private fun setStatsColors(type: String) {
        statsBinding.progressHp.setIndicatorColor(ContextCompat.getColor(requireContext(), setTypeBackgroundDark(type)))
        statsBinding.progressAttack.setIndicatorColor(ContextCompat.getColor(requireContext(), setTypeBackgroundDark(type)))
        statsBinding.progressDefense.setIndicatorColor(ContextCompat.getColor(requireContext(), setTypeBackgroundDark(type)))
        statsBinding.progressSpecialAttack.setIndicatorColor(ContextCompat.getColor(requireContext(), setTypeBackgroundDark(type)))
        statsBinding.progressSpecialDefense.setIndicatorColor(ContextCompat.getColor(requireContext(), setTypeBackgroundDark(type)))
        statsBinding.progressSpeed.setIndicatorColor(ContextCompat.getColor(requireContext(), setTypeBackgroundDark(type)))
    }

    private fun setProgressValues(stat: List<Stats>) {
        statsBinding.progressHp.setProgress(stat[0].baseStat, true)
        statsBinding.progressAttack.setProgress(stat[1].baseStat, true)
        statsBinding.progressDefense.setProgress(stat[2].baseStat, true)
        statsBinding.progressSpecialAttack.setProgress(stat[3].baseStat, true)
        statsBinding.progressSpecialDefense.setProgress(stat[4].baseStat, true)
        statsBinding.progressSpeed.setProgress(stat[5].baseStat, true)
    }

}