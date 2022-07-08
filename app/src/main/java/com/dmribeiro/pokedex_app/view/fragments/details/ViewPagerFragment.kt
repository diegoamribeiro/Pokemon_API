package com.dmribeiro.pokedex_app.view.fragments.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.dmribeiro.pokedex_app.databinding.FragmentViewPagerBinding
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.utils.IndexTabMenu
import com.dmribeiro.pokedex_app.view.fragments.AboutFragment
import com.dmribeiro.pokedex_app.view.fragments.StatsFragment
import com.dmribeiro.pokedex_app.view.fragments.details.DetailsFragment.Companion.POKEMON_NAME
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment(private val currentPokemon: PokemonListener) : Fragment(){

    private lateinit var binding: FragmentViewPagerBinding

    private lateinit var aboutFragment: AboutFragment
    private lateinit var statsFragment: StatsFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        val fragmentList = arrayListOf(
            AboutFragment(currentPokemon),
            StatsFragment(currentPokemon)
        )

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