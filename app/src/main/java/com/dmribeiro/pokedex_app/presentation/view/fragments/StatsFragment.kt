package com.dmribeiro.pokedex_app.presentation.view.fragments

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dmribeiro.pokedex_app.data.remote.model.StatsResponse
import com.dmribeiro.pokedex_app.databinding.FragmentStatsBinding
import com.dmribeiro.pokedex_app.domain.model.Stats
import com.dmribeiro.pokedex_app.presentation.view.fragments.details.PokemonListener
import com.dmribeiro.pokedex_app.utils.setTypeBackgroundDark
import com.dmribeiro.pokedex_app.utils.setTypeBackgroundTranslucent
import com.google.android.material.textview.MaterialTextView


class StatsFragment(val currentPokemon: PokemonListener) : Fragment() {

    private var pokemon: com.dmribeiro.pokedex_app.domain.model.Pokemon? = null
    private lateinit var statsBinding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        statsBinding = FragmentStatsBinding.inflate(layoutInflater)
        pokemon = currentPokemon.callPokemon()
        setStatsColors(pokemon!!.types[0].name)
        setStatsValues(pokemon?.stats!!)
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
        setStatsTrackColors(type)
    }

    private fun setStatsTrackColors(type: String) {
        statsBinding.progressHp.trackColor = Color.parseColor(setTypeBackgroundTranslucent(type))
        statsBinding.progressAttack.trackColor = Color.parseColor(setTypeBackgroundTranslucent(type))
        statsBinding.progressDefense.trackColor = Color.parseColor(setTypeBackgroundTranslucent(type))
        statsBinding.progressSpecialAttack.trackColor = Color.parseColor(setTypeBackgroundTranslucent(type))
        statsBinding.progressSpecialDefense.trackColor = Color.parseColor(setTypeBackgroundTranslucent(type))
        statsBinding.progressSpeed.trackColor = Color.parseColor(setTypeBackgroundTranslucent(type))
    }

    private fun setProgressValues(stat: List<Stats>) {
        with(statsBinding){
            progressHp.setProgress(stat[0].baseStat, true)
            progressAttack.setProgress(stat[1].baseStat, true)
            progressDefense.setProgress(stat[2].baseStat, true)
            progressSpecialAttack.setProgress(stat[3].baseStat, true)
            progressSpecialDefense.setProgress(stat[4].baseStat, true)
            progressSpeed.setProgress(stat[5].baseStat, true)
        }
    }

    private fun setStatsValues(stat: List<Stats>) {
        with(statsBinding){
            animateText(mtvValueHp, stat[0].baseStat)
            animateText(mtvValueAttack, stat[1].baseStat)
            animateText(mtvValueDefense, stat[2].baseStat)
            animateText(mtvValueSpecialAttack, stat[3].baseStat)
            animateText(mtvValueSpecialDefense, stat[4].baseStat)
            animateText(mtvValueSpeed, stat[5].baseStat)
        }
    }

    private fun animateText(mtv: MaterialTextView, statValue: Int) {
        val animator = ValueAnimator()
        animator.setObjectValues(0, statValue)
        animator.addUpdateListener { animation -> mtv.setText(animation.animatedValue.toString()) }
        animator.duration = 1000
        animator.start()
    }
}