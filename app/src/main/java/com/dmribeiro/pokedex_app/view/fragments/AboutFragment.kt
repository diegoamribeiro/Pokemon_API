package com.dmribeiro.pokedex_app.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmribeiro.pokedex_app.databinding.FragmentAboutBinding
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.utils.*
import com.dmribeiro.pokedex_app.utils.setTypeLightColor
import com.dmribeiro.pokedex_app.view.fragments.details.PokemonListener
import java.util.*

class AboutFragment(private val currentPokemon: PokemonListener) : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private var pokemon: Pokemon = currentPokemon.callPokemon()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)

        pokemon = currentPokemon.callPokemon()

        binding.mtvTypeOne.text = pokemon.types[0].type.name.replaceFirstChar { it.uppercase(
            Locale.getDefault()) }
        binding.ivTypeOne.setTypeSymbol(pokemon.types[0].type.name)

        if (pokemon.types.size < 2){
            binding.ivTypeTwo.visibility = View.GONE
            binding.mtvTypeTwo.visibility = View.GONE
        }else{
            binding.ivTypeTwo.setTypeSymbol(pokemon.types[1].type.name)
            binding.mtvTypeTwo.text = pokemon.types[1].type.name.replaceFirstChar { it.uppercase(
                Locale.getDefault()) }
        }

        binding.mtvLabelBaseExperience.setTextTypeLightColor(pokemon.types[0].type.name)
        binding.mtvLabelHeight.setTextTypeLightColor(pokemon.types[0].type.name)
        binding.mtvLabelWeight.setTextTypeLightColor(pokemon.types[0].type.name)

        binding.mtvHeight.text = "${pokemon.height?.heightFormat()}m"
        binding.mtvWeight.text = "${pokemon.weight}lbs"
        binding.mtvBaseExperience.text =  "${pokemon.baseExperience}"

        return binding.root
    }


}