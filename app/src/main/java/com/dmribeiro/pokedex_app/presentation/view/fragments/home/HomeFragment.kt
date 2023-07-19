package com.dmribeiro.pokedex_app.presentation.view.fragments.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmribeiro.pokedex_app.databinding.FragmentHomeBinding
import com.dmribeiro.pokedex_app.presentation.view.adapter.PokemonHomeAdapter
import com.dmribeiro.pokedex_app.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import com.dmribeiro.pokedex_app.presentation.MainActivity
import com.dmribeiro.pokedex_app.R
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.dmribeiro.pokedex_app.presentation.state.Resource
import com.dmribeiro.pokedex_app.utils.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private val homeViewModel: HomeViewModel by viewModels()
    private var lastPosition: Int = 0
    private val homeAdapter: PokemonHomeAdapter by lazy { PokemonHomeAdapter() }
    private lateinit var mLayoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.template_fire_color)
        val mActivity = (activity as MainActivity).supportActionBar
        mActivity?.setBackgroundDrawable(
            resources.getDrawable(
                R.color.template_fire_color,
                resources.newTheme()
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestApiData()
        mLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView = binding.rvList
        recyclerView.apply {
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }
        binding.rvList.showShimmer()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            adapter = homeAdapter
            layoutManager = mLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    lastPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition()
                }
            })
            val getPreferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
            lastPosition = getPreferences.getInt("lastPosition", lastPosition)
            //Log.d("***PositionOnResume -> ", lastPosition.toString())
        }
    }

    private fun requestApiData() {
        lifecycleScope.launch{
            homeViewModel.pokemonResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        binding.rvList.hideShimmer()
                        response.data?.let {
                            Log.d("***Home", it.toString())
                            homeAdapter.setData(it)
                        }
                    }
                    is Resource.Error -> {
                        response.let {
                            binding.rvList.hideShimmer()
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is Resource.Loading -> {
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
        //Log.d("***PositionOnPause -> ", lastPosition.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        val preferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt("lastPosition", 0)
        editor.apply()
        //Log.d("***PositionOnDestroy -> ", lastPosition.toString())
    }

    private fun searchThroughDatabase(pokemon: String) {
        val searchQuery = "%$pokemon%"
        homeViewModel.searchPokemon(searchQuery).observe(this) { list ->
            homeAdapter.setData(list)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Infla o menu; este adiciona itens à barra de ação, se estiver presente.
        inflater.inflate(R.menu.home_fragment_menu, menu)

        // Configura o SearchView
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Trata eventos de clique de item de menu aqui
        return super.onOptionsItemSelected(item)
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