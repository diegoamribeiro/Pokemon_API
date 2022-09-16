package com.dmribeiro.pokedex_app.view.fragments.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
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
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.dmribeiro.pokedex_app.remote.ResponseViewState
import com.dmribeiro.pokedex_app.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mLayoutManager: GridLayoutManager
    private var lastPosition: Int = 0
    private val homeAdapter: PokemonHomeAdapter by lazy { PokemonHomeAdapter() }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.template_fire_color)
        val mActivity = (activity as MainActivity).supportActionBar
        mActivity?.setBackgroundDrawable(resources.getDrawable(R.color.template_fire_color, resources.newTheme()))
        mLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding.rvList.showShimmer()
        requestApiData()
        setupRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setupRecyclerView(){
        recyclerView = binding.rvList
        recyclerView.apply {
            adapter = homeAdapter
            layoutManager = mLayoutManager
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    lastPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition()
                }
            })
            //homeViewModel.saveScrollPosition(lastPosition)
            val getPreferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
            lastPosition = getPreferences.getInt("lastPosition", 0)
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

//    private fun searChThroughDatabase(query: String){
//        val searchQuery = "%$query%"
//        homeViewModel.searchDatabase(searchQuery).observe(this, {list->
//            list?.let {
//                mAdapter.setData(it)
//            }
//        })
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_menu, menu)
        val menuSearch = menu.findItem(R.id.menu_search)
        menuSearch.icon?.setTint(resources.getColor(R.color.template_fire_color))
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        recyclerView.scrollToPosition(lastPosition)
    }

    override fun onPause() {
        super.onPause()
        val preferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt("lastPosition", lastPosition)
        editor.apply()
//        lifecycleScope.launch {
//            recyclerView.scrollToPosition(homeViewModel.getScrollPosition())
//            Log.d("***ScrollPosition -> ", homeViewModel.getScrollPosition().toString())
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val preferences: SharedPreferences = getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt("lastPosition", 0)
        editor.apply()
        //val preferences = UserPreferencesRepository(requireContext())
//        lifecycleScope.launch {
//            preferences.deleteAllData()
//            recyclerView.scrollToPosition(homeViewModel.getScrollPosition())
//        }
    }

}