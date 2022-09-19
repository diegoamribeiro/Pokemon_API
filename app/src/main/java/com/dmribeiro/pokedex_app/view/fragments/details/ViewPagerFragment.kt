package com.dmribeiro.pokedex_app.view.fragments.details

import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dmribeiro.pokedex_app.databinding.FragmentViewPagerBinding
import com.dmribeiro.pokedex_app.utils.IndexTabMenu
import com.dmribeiro.pokedex_app.utils.setTypeBackgroundLight
import com.dmribeiro.pokedex_app.utils.setTypeBackgroundTint
import com.dmribeiro.pokedex_app.view.fragments.AboutFragment
import com.dmribeiro.pokedex_app.view.fragments.EvolutionFragment
import com.dmribeiro.pokedex_app.view.fragments.StatsFragment
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment(private val currentPokemon: PokemonListener) : Fragment(){

    private lateinit var binding: FragmentViewPagerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        val fragmentList = arrayListOf(
            AboutFragment(currentPokemon),
            StatsFragment(currentPokemon),
            EvolutionFragment()
        )
        binding.tabLayout.setSelectedTabIndicatorColor(Color.parseColor(setTypeBackgroundTint(currentPokemon.callPokemon().types[0].type.name)))
        binding.tabLayout.setTabRippleColorResource(setTypeBackgroundLight(currentPokemon.callPokemon().types[0].type.name))
        binding.tabLayout.setTabTextColors(Color.parseColor("#BABABA"),
            Color.parseColor(setTypeBackgroundTint(currentPokemon.callPokemon().types[0].type.name)))



        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle)

        binding.fragViewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.fragViewPager){tab, position ->
            tab.text = IndexTabMenu.values()[position].title
        }.attach()
            return binding.root
    }

}