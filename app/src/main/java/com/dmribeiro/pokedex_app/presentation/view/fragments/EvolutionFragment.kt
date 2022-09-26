package com.dmribeiro.pokedex_app.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmribeiro.pokedex_app.R
import com.dmribeiro.pokedex_app.databinding.FragmentEvolutionBinding


class EvolutionFragment : Fragment() {

    private lateinit var binding: FragmentEvolutionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvolutionBinding.inflate(layoutInflater)



        return binding.root
    }

}