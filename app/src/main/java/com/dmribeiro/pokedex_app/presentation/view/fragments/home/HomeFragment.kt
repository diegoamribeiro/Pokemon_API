package com.dmribeiro.pokedex_app.presentation.view.fragments.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
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
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.dmribeiro.pokedex_app.presentation.state.Resource
import com.dmribeiro.pokedex_app.presentation.view.fragments.OverlayDialogFragment
import com.dmribeiro.pokedex_app.utils.viewBinding
import kotlinx.coroutines.launch
import android.graphics.Path


@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var recyclerView: RecyclerView
    private val homeViewModel: HomeViewModel by viewModels()
    private var lastPosition: Int = 0
    private val homeAdapter: PokemonHomeAdapter by lazy { PokemonHomeAdapter() }
    private lateinit var mLayoutManager: GridLayoutManager
    private var overlay: Bitmap? = null
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

        showDialog()

//        binding.btZap.setOnTouchListener { view, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    dX = view.x - event.rawX
//                    dY = view.y - event.rawY
//                    true
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    view.animate()
//                        .x(event.rawX + dX)
//                        .y(event.rawY + dY)
//                        .setDuration(0)
//                        .start()
//                    redrawHoleForButton()  // Esta função irá redesenhar o buraco no overlay
//                    true
//                }
//                else -> false
//            }
//        }

        // Chamar showDialog() depois que o layout for processado
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remover o listener para que não seja chamado novamente
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                //binding.btZap.drawHighlight()
            }
        })
    }

    private fun redrawHoleForButton() {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // Verifique se o overlay já foi criado
        if (overlay == null) {
            overlay = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(overlay!!)
        // Limpe o overlay antes de desenhar
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        // Preencha o overlay
        canvas.drawColor(Color.parseColor("#80000000"))

        val coords = IntArray(2)
        binding.btZap.getLocationInWindow(coords)
        val buttonX = coords[0]
        val buttonY = coords[1] - statusBarHeightNew()
        val buttonWidth = binding.btZap.width
        val buttonHeight = binding.btZap.height

        val clearPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            style = Paint.Style.FILL
        }

        canvas.drawRect(
            buttonX.toFloat(),
            buttonY.toFloat(),
            (buttonX + buttonWidth).toFloat(),
            (buttonY + buttonHeight).toFloat(),
            clearPaint
        )

        binding.ivOverlayWithHole.setImageBitmap(overlay)
    }


    private fun statusBarHeightNew(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }


    fun View.drawHighlight(tooltipText: String = "") {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val overlay = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(overlay)
        canvas.drawColor(Color.parseColor("#80000000"))

        val coordinates = IntArray(2)
        this.getLocationInWindow(coordinates)
        val viewX = coordinates[0]
        val viewY = coordinates[1] - statusBarHeight(this.context)
        val viewWidth = this.width
        val viewHeight = this.height

        val clearPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            style = Paint.Style.FILL
        }

        canvas.drawRect(
            viewX.toFloat() - 10, // Largura do lado esquerdo
            viewY.toFloat() - 10, // Altura do top
            (viewX + viewWidth).toFloat() + 10f, // Largura do lado direito
            (viewY + viewHeight).toFloat() + 10f, // Altura da base
            clearPaint
        )

        // Verifica se o pai da View é um ViewGroup
        val parent = this.parent
        if (parent is ViewGroup) {
            // Cria o ImageView
            val overlayImageView = ImageView(this.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setImageBitmap(overlay)
                visibility = View.VISIBLE
            }

            // Adiciona o ImageView ao ViewGroup
            parent.addView(overlayImageView)
        }

        val margin = 10f
        val arrowHeight = 15f // Altura da seta
        val arrowWidth = 20f // Largura da base da seta

        if (tooltipText.isNotEmpty()) {
            val textPaint = Paint().apply {
                color = Color.WHITE
                textSize = 12f * resources.displayMetrics.scaledDensity // 12sp
                isAntiAlias = true
            }

            val padding = 20f // 20f padding
            val textWidth = textPaint.measureText(tooltipText)
            val balloonWidth = textWidth + 2 * padding
            val balloonHeight = textPaint.textSize + 2 * padding

            var balloonX = (viewX + viewWidth / 2) - balloonWidth / 2
            var balloonY = viewY - balloonHeight - padding - arrowHeight // considerando a altura da seta

            var arrowDirection = "TOP"

            when {
                balloonY - margin >= 0 -> {
                    // Mantém a posição atual (acima da View)
                }
                balloonY + balloonHeight + viewHeight + 2 * padding + arrowHeight + margin <= screenHeight -> {
                    // Abaixo da View
                    balloonY = viewY + viewHeight + padding
                    arrowDirection = "BOTTOM"
                }
                viewX + viewWidth + balloonWidth + padding + margin <= screenWidth -> {
                    // Lado direito da View
                    balloonX = viewX + viewWidth + padding
                    balloonY = viewY + (viewHeight - balloonHeight) / 2
                    arrowDirection = "RIGHT"
                }
                viewX - balloonWidth - padding - margin >= 0 -> {
                    // Lado esquerdo da View
                    balloonX = viewX - balloonWidth - padding
                    balloonY = viewY + (viewHeight - balloonHeight) / 2
                    arrowDirection = "LEFT"
                }
            }

            // Garantindo que o tooltip não ultrapasse as bordas da tela
            balloonX = balloonX.coerceAtLeast(margin).coerceAtMost(screenWidth - balloonWidth - margin)
            balloonY = balloonY.coerceAtLeast(margin).coerceAtMost(screenHeight - balloonHeight - margin)

            val rect = RectF(balloonX, balloonY, balloonX + balloonWidth, balloonY + balloonHeight)
            val balloonPaint = Paint().apply {
                color = Color.BLACK
                style = Paint.Style.FILL
                isAntiAlias = true
            }
            canvas.drawRoundRect(rect, 25f, 25f, balloonPaint)

            // Desenhar a seta
            val path = Path()
            when (arrowDirection) {
                "TOP" -> {
                    path.moveTo((viewX + viewWidth / 2) - arrowWidth / 2, viewY.toFloat())
                    path.lineTo(viewX + viewWidth / 2f, viewY - arrowHeight)
                    path.lineTo((viewX + viewWidth / 2) + arrowWidth / 2, viewY.toFloat())
                    path.close()
                }
                "BOTTOM" -> {
                    path.moveTo((viewX + viewWidth / 2) - arrowWidth / 2, viewY + viewHeight.toFloat())
                    path.lineTo(viewX + viewWidth / 2f, viewY + viewHeight + arrowHeight)
                    path.lineTo((viewX + viewWidth / 2) + arrowWidth / 2, viewY + viewHeight.toFloat())
                    path.close()
                }
                "LEFT" -> {
                    path.moveTo(viewX.toFloat(), (viewY + viewHeight / 2) - arrowWidth / 2)
                    path.lineTo(viewX - arrowHeight, viewY + viewHeight / 2f)
                    path.lineTo(viewX.toFloat(), (viewY + viewHeight / 2) + arrowWidth / 2)
                    path.close()
                }
                "RIGHT" -> {
                    path.moveTo(viewX + viewWidth.toFloat(), (viewY + viewHeight / 2) - arrowWidth / 2)
                    path.lineTo(viewX + viewWidth + arrowHeight, viewY + viewHeight / 2f)
                    path.lineTo(viewX + viewWidth.toFloat(), (viewY + viewHeight / 2) + arrowWidth / 2)
                    path.close()
                }
            }



            canvas.drawPath(path, balloonPaint)

            // Desenhar texto
            val textX = balloonX + padding
            val textY = balloonY + balloonHeight / 2 + textPaint.textSize / 3
            canvas.drawText(tooltipText, textX, textY, textPaint)
        }
    }

    // Função auxiliar para obter a altura da barra de status e usar como ponto de referência
    private fun statusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    private fun showDialog() {
        val dialog = OverlayDialogFragment()
        dialog.onShowButtonClick = {
            binding.btZap.drawHighlight("Aqui um texto informando \no que tem de novidade.")
        }
        dialog.show(childFragmentManager, "DIALOG")
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