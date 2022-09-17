package com.dmribeiro.pokedex_app.view.fragments.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
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
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.dmribeiro.pokedex_app.remote.ResponseViewState
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener, MenuProvider {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private var lastPosition: Int = 0
    private val homeAdapter: PokemonHomeAdapter by lazy { PokemonHomeAdapter() }
    private lateinit var mLayoutManager: GridLayoutManager

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.template_fire_color)
        val mActivity = (activity as MainActivity).supportActionBar
        mActivity?.setBackgroundDrawable(
            resources.getDrawable(
                R.color.template_fire_color,
                resources.newTheme()
            )
        )
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        mLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView = binding.rvList
        binding.rvList.showShimmer()
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        search.icon?.setTint(resources.getColor(R.color.template_fire_color))
        requestApiData()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            adapter = homeAdapter
            layoutManager = mLayoutManager
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    lastPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition()
                }
            })
            val getPreferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
            lastPosition = getPreferences.getInt("lastPosition", lastPosition)
            Log.d("***PositionOnResume -> ", lastPosition.toString())
        }
    }

    private fun requestApiData() {
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
                        recyclerView.scrollToPosition(lastPosition)
                    }
                    is ResponseViewState.Error -> {
                        response.let {
                            binding.rvList.hideShimmer()
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is ResponseViewState.Loading -> {
                        binding.rvList.showShimmer()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val preferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt("lastPosition", lastPosition)
        editor.apply()
        Log.d("***PositionOnPause -> ", lastPosition.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        val preferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt("lastPosition", 0)
        editor.apply()
        Log.d("***PositionOnDestroy -> ", lastPosition.toString())
    }

    private fun searchThroughDatabase(pokemon: String) {
        val searchQuery = "%$pokemon%"
        homeViewModel.searchPokemon(searchQuery).observe(this) { list ->
            homeAdapter.setData(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchThroughDatabase(newText)
        }
        return true
    }

}