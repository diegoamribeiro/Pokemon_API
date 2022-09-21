package com.dmribeiro.pokedex_app.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dmribeiro.pokedex_app.R
import com.dmribeiro.pokedex_app.databinding.FragmentEvolutionBinding
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.remote.ResponseViewState
import com.dmribeiro.pokedex_app.view.fragments.details.PokemonListener
import com.dmribeiro.pokedex_app.viewmodel.DetailsViewModel
import kotlinx.coroutines.launch


class EvolutionFragment(val currentPokemon: PokemonListener) : Fragment() {

    private lateinit var binding: FragmentEvolutionBinding
    private lateinit var detailsViewModel: DetailsViewModel
    private var pokemon: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvolutionBinding.inflate(layoutInflater)
        detailsViewModel = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]
        pokemon = currentPokemon.callPokemon()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getEvolutionChain(pokemon!!.name)
    }


    private fun getEvolutionChain(name: String) {
        detailsViewModel.getEvolutionChain(name)
        detailsViewModel.evolutionChain.observe(viewLifecycleOwner){response ->
            when(response){
                is ResponseViewState.Success -> {
                    Log.d("***EvolutionFragment", response.data.toString())
                }
                is ResponseViewState.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
                is ResponseViewState.Loading -> {
                    Log.d("***Loading", response.toString())
                }
            }
        }
    }

}