package com.dmribeiro.pokedex_app.view.fragments.home

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmribeiro.pokedex_app.databinding.FragmentHomeBinding
import com.dmribeiro.pokedex_app.view.adapter.PokemonHomeAdapter
import com.dmribeiro.pokedex_app.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import com.dmribeiro.pokedex_app.MainActivity
import com.dmribeiro.pokedex_app.R
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dmribeiro.pokedex_app.remote.ResponseViewState
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private val homeAdapter: PokemonHomeAdapter by lazy { PokemonHomeAdapter() }
    private lateinit var recyclerView: RecyclerView
    private lateinit var mLayoutManager: GridLayoutManager
    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()

        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.template_fire_color)
        val mActivity = (activity as MainActivity).supportActionBar
        mActivity?.setBackgroundDrawable(resources.getDrawable(R.color.template_fire_color, resources.newTheme()))

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        requestApiData()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setupRecyclerView(){
        binding.rvList.showShimmer()
        recyclerView = binding.rvList
        recyclerView.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }
    }

    private fun requestApiData(){
        lifecycleScope.launch {
            homeViewModel.getAllPokemon()
            homeViewModel.pokemonResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is ResponseViewState.Success -> {
                        binding.rvList.hideShimmer()
                        response.data?.let {
                            homeAdapter.setData(it)
                            Log.d("**Data", it.toString())
                        }
                    }
                    is ResponseViewState.Error -> {
                        response.let {
                            binding.rvList.hideShimmer()
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    is ResponseViewState.Loading ->{
                        binding.rvList.showShimmer()
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_menu, menu)
        val menuSearch = menu.findItem(R.id.menu_search)
        menuSearch.icon.setTint(resources.getColor(R.color.template_fire_color))
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

}