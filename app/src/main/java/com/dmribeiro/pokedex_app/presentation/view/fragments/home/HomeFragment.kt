package com.dmribeiro.pokedex_app.presentation.view.fragments.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmribeiro.pokedex_app.databinding.FragmentHomeBinding
import com.dmribeiro.pokedex_app.presentation.view.adapter.PokemonHomeAdapter
import com.dmribeiro.pokedex_app.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import android.view.*
import androidx.appcompat.widget.SearchView
import com.dmribeiro.pokedex_app.presentation.MainActivity
import com.dmribeiro.pokedex_app.R
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.dmribeiro.pokedex_app.presentation.state.Resource
import com.dmribeiro.pokedex_app.presentation.view.fragments.OverlayDialogFragment
import com.dmribeiro.pokedex_app.utils.viewBinding
import kotlinx.coroutines.launch
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.widget.ImageView
import android.widget.PopupWindow
import com.dmribeiro.pokedex_app.utils.Highlighter
import java.lang.Math.sqrt
import kotlin.math.pow


@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var recyclerView: RecyclerView
    private val homeViewModel: HomeViewModel by viewModels()
    private var lastPosition: Int = 0
    private val homeAdapter: PokemonHomeAdapter by lazy { PokemonHomeAdapter() }
    private lateinit var mLayoutManager: GridLayoutManager
    private lateinit var highlighter: Highlighter

    private var dX = 0f
    private var dY = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        highlighter = Highlighter(requireContext())
        requestApiData()
        mLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView = binding.rvList
        recyclerView.apply {
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }
        //binding.rvList.showShimmer()
        setupRecyclerView()

        //showDialog()

//        binding.btZap.setOnTouchListener { view, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    dX = view.x - event.rawX
//                    dY = view.y - event.rawY
//                    true
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    val newX = event.rawX + dX
//                    val newY = event.rawY + dY
//                    view.animate()
//                        .x(newX)
//                        .y(newY)
//                        .setDuration(0)
//                        .start()
//                    highlighter.drawHighlight(view, "Aqui o texto de \nteste \nmais texto mais texto mais texto. \nquebra.")
//                    true
//                }
//
//                else -> false
//            }
//        }


        // Chamar showDialog() depois que o layout for processado
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remover o listener para que não seja chamado novamente
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                highlighter.drawHighlight(binding.btZap, "Aqui o texto de \nteste \nmais texto mais texto mais texto. \nquebra.")

            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        highlighter.dismiss()
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
        lifecycleScope.launch {
            homeViewModel.pokemonResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        //binding.rvList.hideShimmer()
                        response.data?.let {
                            homeAdapter.setData(it)
                        }
                    }

                    is Resource.Error -> {
                        response.let {
                            //binding.rvList.hideShimmer()
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    is Resource.Loading -> {
                        //binding.rvList.showShimmer()
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