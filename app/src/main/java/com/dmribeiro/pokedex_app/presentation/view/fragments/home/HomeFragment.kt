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
    private var overlayImageView: ImageView? = null

    private var dX = 0f
    private var dY = 0f
    private var balloonY = 0f


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
        //binding.rvList.showShimmer()
        setupRecyclerView()

        //showDialog()

        binding.btZap.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newY = event.rawY + dY
                    view.animate()
                        .x(newX)
                        .y(newY)
                        .setDuration(0)
                        .start()
                    binding.btZap.drawHighlightDef("Aqui o texto de \nteste \nmais texto mais texto mais texto. \nquebra.")
                    true
                }

                else -> false
            }
        }


        // Chamar showDialog() depois que o layout for processado
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remover o listener para que não seja chamado novamente
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                binding.btZap.post {
                    binding.btZap.drawHighlightDef("Aqui o texto de \nteste \nmais texto mais texto mais texto. \nquebra.")
                }
            }
        })
    }


//    private fun View.drawHighlightDef(tooltipText: String = "") {
//        val displayMetrics = resources.displayMetrics
//        val screenWidth = displayMetrics.widthPixels
//        val screenHeight = displayMetrics.heightPixels
//
//        val overlay = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(overlay)
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
//        canvas.drawColor(Color.parseColor("#80000000"))
//
//        val rect = Rect()
//        this.getWindowVisibleDisplayFrame(rect)
//        val viewX = rect.left
//        val viewY = rect.top
//        val viewWidth = rect.width()
//        val viewHeight = rect.height()
//
//        val clearPaint = Paint().apply {
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
//            style = Paint.Style.FILL
//        }
//
//        canvas.drawRect(
//            viewX.toFloat() - 10, // Largura do lado esquerdo
//            viewY.toFloat() - 10, // Altura do top
//            (viewX + viewWidth).toFloat() + 10f, // Largura do lado direito
//            (viewY + viewHeight).toFloat() + 10f, // Altura da base
//            clearPaint
//        )
//
//        Log.d("HighlightDebug", "X: $viewX, Y: $viewY, Width: $viewWidth, Height: $viewHeight")
//
//        // Verifica se o pai da View é um ViewGroup
//        val parent = this.parent
//        if (parent is ViewGroup) {
//            // Cria o ImageView
//            overlayImageView?.let {
//                (it.parent as? ViewGroup)?.removeView(it)
//            }
//
//            overlayImageView = ImageView(this.context).apply {
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//                setImageBitmap(overlay)
//                scaleType = ImageView.ScaleType.FIT_XY
//                visibility = View.VISIBLE
//                elevation = this@drawHighlightDef.elevation + 1f
//            }
//
//            parent.addView(overlayImageView, parent.indexOfChild(this@drawHighlightDef) + 1) // Adicione o ImageView ACIMA da View destacada
//        }
//
//        val margin = 10f
//        val arrowHeight = 15f // Altura da seta
//        val arrowWidth = 20f // Largura da base da seta
//
//        if (tooltipText.isNotEmpty()) {
//            val textPaint = Paint().apply {
//                color = Color.WHITE
//                textSize = 12f * resources.displayMetrics.scaledDensity
//                isAntiAlias = true
//            }
//
//            // Divida o texto por quebras de linha para desenhar cada linha separadamente
//            val lines = tooltipText.split("\n")
//            val textHeight = textPaint.textSize
//            val maxWidth = lines.maxOf { textPaint.measureText(it) }
//            val padding = 20f // 20f padding
//            val balloonWidth = maxWidth + 2 * padding
//            // Calcule a altura total do texto considerando todas as linhas e o espaçamento entre elas
//            val totalTextHeight = lines.size * textHeight + (lines.size - 1) * 5f  // supondo 5f como espaçamento entre linhas
//
//            // Atualize a altura do balloon de acordo com a altura total do texto
//            val balloonHeight = totalTextHeight + 2 * padding
//
//            var balloonX = (viewX + viewWidth / 2) - balloonWidth / 2
//            var balloonY =
//                viewY - balloonHeight - padding - arrowHeight // considerando a altura da seta
//
//            var arrowDirection = "TOP"
//
//            when {
//                balloonY - margin >= 0 -> {
//                    // Mantém a posição atual (acima da View)
//                }
//
//                balloonY + balloonHeight + viewHeight + 2 * padding + arrowHeight + margin <= screenHeight -> {
//                    // Abaixo da View
//                    balloonY = viewY + viewHeight + padding
//                    arrowDirection = "BOTTOM"
//                }
//
//                viewX + viewWidth + balloonWidth + padding + margin <= screenWidth -> {
//                    // Lado direito da View
//                    balloonX = viewX + viewWidth + padding
//                    balloonY = viewY + (viewHeight - balloonHeight) / 2
//                    arrowDirection = "RIGHT"
//                }
//
//                viewX - balloonWidth - padding - margin >= 0 -> {
//                    // Lado esquerdo da View
//                    balloonX = viewX - balloonWidth - padding
//                    balloonY = viewY + (viewHeight - balloonHeight) / 2
//                    arrowDirection = "LEFT"
//                }
//            }
//
//            // Garantindo que o tooltip não ultrapasse as bordas da tela
//            balloonX =
//                balloonX.coerceAtLeast(margin).coerceAtMost(screenWidth - balloonWidth - margin)
//            balloonY =
//                balloonY.coerceAtLeast(margin).coerceAtMost(screenHeight - balloonHeight - margin)
//
//            val rect = RectF(balloonX, balloonY, balloonX + balloonWidth, balloonY + balloonHeight)
//            val balloonPaint = Paint().apply {
//                color = Color.BLACK
//                style = Paint.Style.FILL
//                isAntiAlias = true
//            }
//            canvas.drawRoundRect(rect, 25f, 25f, balloonPaint)
//
//            // Desenhar a seta
//            val path = Path()
//            when (arrowDirection) {
//                "TOP" -> {
//                    path.moveTo((viewX + viewWidth / 2) - arrowWidth / 2, balloonY + balloonHeight)
//                    path.lineTo(viewX + viewWidth / 2f, balloonY + balloonHeight + arrowHeight)
//                    path.lineTo((viewX + viewWidth / 2) + arrowWidth / 2, balloonY + balloonHeight)
//                    path.close()
//                }
//
//                "BOTTOM" -> {
//                    path.moveTo((viewX + viewWidth / 2) - arrowWidth / 2, balloonY)
//                    path.lineTo(viewX + viewWidth / 2f, balloonY - arrowHeight)
//                    path.lineTo((viewX + viewWidth / 2) + arrowWidth / 2, balloonY)
//                    path.close()
//                }
//
//                "LEFT" -> {
//                    path.moveTo(balloonX + balloonWidth, (viewY + viewHeight / 2) - arrowWidth / 2)
//                    path.lineTo(balloonX + balloonWidth + arrowHeight, viewY + viewHeight / 2f)
//                    path.lineTo(balloonX + balloonWidth, (viewY + viewHeight / 2) + arrowWidth / 2)
//                    path.close()
//                }
//
//                "RIGHT" -> {
//                    path.moveTo(balloonX, (viewY + viewHeight / 2) - arrowWidth / 2)
//                    path.lineTo(balloonX - arrowHeight, viewY + viewHeight / 2f)
//                    path.lineTo(balloonX, (viewY + viewHeight / 2) + arrowWidth / 2)
//                    path.close()
//                }
//            }
//
//            canvas.drawPath(path, balloonPaint)
//
//            // Desenhar texto
//            val textStartY = balloonY + padding - textPaint.ascent()
//
//            // Desenhe cada linha de texto
//            var currentTextY = textStartY
//            for (line in lines) {
//                val textX = balloonX + padding
//                canvas.drawText(line, textX, currentTextY, textPaint)
//                currentTextY += textHeight + 5f
//            }
//        }
//    }

    private var popupWindow: PopupWindow? = null

    private fun View.drawHighlightDef(tooltipText: String = "") {
        val closeX = 0f
        val closeY = 0f
        fun draw() {
            val popupView = object : View(context) {
                override fun onDraw(canvas: Canvas) {
                    super.onDraw(canvas)
                    canvas.drawColor(Color.parseColor("#80000000"))

                    val clearPaint = Paint().apply {
                        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                        style = Paint.Style.FILL
                    }

                    val coordinates = IntArray(2)
                    this@drawHighlightDef.getLocationOnScreen(coordinates)
                    val statusBarHeight: Int = getStatusBarHeight()
                    val viewX = coordinates[0].toFloat()
                    val viewY = coordinates[1].toFloat() - statusBarHeight
                    val viewWidth = this@drawHighlightDef.width.toFloat()
                    val viewHeight = this@drawHighlightDef.height.toFloat()
                    val displayMetrics = Resources.getSystem().displayMetrics
                    val screenHeight = displayMetrics.heightPixels
                    val screenWidth = displayMetrics.widthPixels

                    // Desenhar o retângulo transparente
                    canvas.drawRect(
                        viewX - 10,
                        viewY - 10,
                        viewX + viewWidth + 10,
                        viewY + viewHeight + 10,
                        clearPaint
                    )

                    val margin = 10f
                    val arrowHeight = 15f
                    val arrowWidth = 20f
                    val rect = RectF(viewX, viewY, viewX + viewWidth, viewY + viewHeight)


                    if (tooltipText.isNotEmpty()) {
                        val textPaint = Paint().apply {
                            color = Color.WHITE
                            textSize = 12f * resources.displayMetrics.scaledDensity
                            isAntiAlias = true
                        }

                        val lines = tooltipText.split("\n")
                        val textHeight = textPaint.textSize
                        val maxWidth = lines.maxOf { textPaint.measureText(it) }
                        val padding = 20f
                        val balloonWidth = maxWidth + 2 * padding
                        val totalTextHeight = lines.size * textHeight + (lines.size - 1) * 5f

                        val balloonHeight = totalTextHeight + 2 * padding

                        var balloonX = (rect.left + rect.width() / 2) - balloonWidth / 2
                        var balloonY = rect.top - balloonHeight - padding - arrowHeight

                        var arrowDirection = "TOP"

                        when {
                            balloonY - margin >= 0 -> { /* Mantém a posição atual (acima da View) */ }

                            balloonY + balloonHeight + rect.height() + 2 * padding + arrowHeight + margin <= screenHeight -> {
                                balloonY = rect.bottom + padding
                                arrowDirection = "BOTTOM"
                            }

                            rect.right + balloonWidth + padding + margin <= screenWidth -> {
                                balloonX = rect.right + padding
                                balloonY = rect.top + (rect.height() - balloonHeight) / 2
                                arrowDirection = "RIGHT"
                            }

                            rect.left - balloonWidth - padding - margin >= 0 -> {
                                balloonX = rect.left - balloonWidth - padding
                                balloonY = rect.top + (rect.height() - balloonHeight) / 2
                                arrowDirection = "LEFT"
                            }
                        }

                        balloonX = balloonX.coerceAtLeast(margin).coerceAtMost(screenWidth - balloonWidth - margin)
                        balloonY = balloonY.coerceAtLeast(margin).coerceAtMost(screenHeight - balloonHeight - margin)

                        val rectF = RectF(balloonX, balloonY, balloonX + balloonWidth, balloonY + balloonHeight)
                        val balloonPaint = Paint().apply {
                            color = Color.BLACK
                            style = Paint.Style.FILL
                            isAntiAlias = true
                        }
                        canvas.drawRoundRect(rectF, 25f, 25f, balloonPaint)

                        val path = Path()
                        when (arrowDirection) {
                            "TOP" -> {
                                path.moveTo((rect.left + rect.width() / 2) - arrowWidth / 2, balloonY + balloonHeight)
                                path.lineTo(rect.left + rect.width() / 2f, balloonY + balloonHeight + arrowHeight)
                                path.lineTo((rect.left + rect.width() / 2) + arrowWidth / 2, balloonY + balloonHeight)
                                path.close()
                            }

                            "BOTTOM" -> {
                                path.moveTo((rect.left + rect.width() / 2) - arrowWidth / 2, balloonY)
                                path.lineTo(rect.left + rect.width() / 2f, balloonY - arrowHeight)
                                path.lineTo((rect.left + rect.width() / 2) + arrowWidth / 2, balloonY)
                                path.close()
                            }

                            "LEFT" -> {
                                path.moveTo(balloonX + balloonWidth, (rect.top + rect.height() / 2) - arrowWidth / 2)
                                path.lineTo(balloonX + balloonWidth + arrowHeight, rect.top + rect.height() / 2f)
                                path.lineTo(balloonX + balloonWidth, (rect.top + rect.height() / 2) + arrowWidth / 2)
                                path.close()
                            }

                            "RIGHT" -> {
                                path.moveTo(balloonX, (rect.top + rect.height() / 2) - arrowWidth / 2)
                                path.lineTo(balloonX - arrowHeight, rect.top + rect.height() / 2f)
                                path.lineTo(balloonX, (rect.top + rect.height() / 2) + arrowWidth / 2)
                                path.close()
                            }
                        }

                        canvas.drawPath(path, balloonPaint)

                        val textStartY = balloonY + padding - textPaint.ascent()
                        var currentTextY = textStartY
                        for (line in lines) {
                            val textX = balloonX + padding
                            canvas.drawText(line, textX, currentTextY, textPaint)
                            currentTextY += textHeight + 5f
                        }

                        val closeText = "X"
                        val closePaint = Paint().apply {
                            color = Color.WHITE
                            textSize = 30f // Defina o tamanho do texto conforme necessário
                            textAlign = Paint.Align.CENTER
                        }
                        val closeDimensionX = balloonX + balloonWidth - 30 // 30 é um valor de exemplo, ajuste conforme necessário
                        val closeDimensionY = balloonY + 30 // 30 é um valor de exemplo, ajuste conforme necessário
                        canvas.drawText(closeText, closeDimensionX, closeDimensionY, closePaint)
                    }
                }
            }
            popupView.setOnClickListener { v ->
                val x = v.x
                val y = v.y

                // Verificando se o clique foi na área do "X"
                if (x >= closeX - 30 && x <= closeX + 30 && y >= closeY - 30 && y <= closeY + 30) {
                    popupWindow?.dismiss()
                    popupWindow = null
                }
            }

            popupWindow?.dismiss()
            popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                false
            )

            popupWindow?.showAtLocation(this, Gravity.NO_GRAVITY, 0, 0)
        }

        draw()
    }

    private fun View.getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }








    private fun statusBarHeightNew(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }


    //    private fun drawHighlight(targetView: View, tooltipText: String = "") {
//        val displayMetrics = targetView.resources.displayMetrics
//        val screenWidth = displayMetrics.widthPixels
//        val screenHeight = displayMetrics.heightPixels
//
//        val overlay = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(overlay)
//        canvas.drawColor(Color.parseColor("#80000000"))
//
//        val coordinates = IntArray(2)
//        targetView.getLocationInWindow(coordinates)
//        val viewX = coordinates[0]
//        val viewY = coordinates[1] - statusBarHeight(targetView.context)
//        val viewWidth = targetView.width
//        val viewHeight = targetView.height
//
//        val clearPaint = Paint().apply {
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
//            style = Paint.Style.FILL
//        }
//
//        canvas.drawRect(
//            viewX.toFloat() - 10, // Largura do lado esquerdo
//            viewY.toFloat() - 10, // Altura do top
//            (viewX + viewWidth).toFloat() + 10f, // Largura do lado direito
//            (viewY + viewHeight).toFloat() + 10f, // Altura da base
//            clearPaint
//        )
//
//
//        val margin = 10f
//        val arrowHeight = 15f // Altura da seta
//        val arrowWidth = 20f // Largura da base da seta
//
//
//        if (tooltipText.isNotEmpty()) {
//            val textPaint = Paint().apply {
//                color = Color.WHITE
//                textSize = 12f * resources.displayMetrics.scaledDensity
//                isAntiAlias = true
//            }
//
//            // Divida o texto por quebras de linha para desenhar cada linha separadamente
//            val lines = tooltipText.split("\n")
//            val textHeight = textPaint.textSize
//            val textWidth = textPaint.measureText(tooltipText)
//            val padding = 20f // 20f padding
//            val balloonWidth = textWidth + 2 * padding
//            // Calcule a altura total do texto considerando todas as linhas e o espaçamento entre elas
//            val totalTextHeight = lines.size * textHeight + (lines.size - 1) * 5f  // supondo 5f como espaçamento entre linhas
//
//
//            // Atualize a altura do balloon de acordo com a altura total do texto
//            val balloonHeight = totalTextHeight + 2 * padding
//
//            var balloonX = (viewX + viewWidth / 2) - balloonWidth / 2
//            var balloonY = viewY - balloonHeight - padding - arrowHeight // considerando a altura da seta
//
//            var arrowDirection = "TOP"
//
//            when {
//                balloonY - margin >= 0 -> {
//                    // Mantém a posição atual (acima da View)
//                }
//                balloonY + balloonHeight + viewHeight + 2 * padding + arrowHeight + margin <= screenHeight -> {
//                    // Abaixo da View
//                    balloonY = viewY + viewHeight + padding
//                    arrowDirection = "BOTTOM"
//                }
//                viewX + viewWidth + balloonWidth + padding + margin <= screenWidth -> {
//                    // Lado direito da View
//                    balloonX = viewX + viewWidth + padding
//                    balloonY = viewY + (viewHeight - balloonHeight) / 2
//                    arrowDirection = "RIGHT"
//                }
//                viewX - balloonWidth - padding - margin >= 0 -> {
//                    // Lado esquerdo da View
//                    balloonX = viewX - balloonWidth - padding
//                    balloonY = viewY + (viewHeight - balloonHeight) / 2
//                    arrowDirection = "LEFT"
//                }
//            }
//
//            // Garantindo que o tooltip não ultrapasse as bordas da tela
//            balloonX = balloonX.coerceAtLeast(margin).coerceAtMost(screenWidth - balloonWidth - margin)
//            balloonY = balloonY.coerceAtLeast(margin).coerceAtMost(screenHeight - balloonHeight - margin)
//
//            val rect = RectF(balloonX, balloonY, balloonX + balloonWidth, balloonY + balloonHeight)
//            val balloonPaint = Paint().apply {
//                color = Color.BLACK
//                style = Paint.Style.FILL
//                isAntiAlias = true
//            }
//            canvas.drawRoundRect(rect, 25f, 25f, balloonPaint)
//
//            // Desenhar a seta
//            val path = Path()
//            when (arrowDirection) {
//                "TOP" -> {
//                    path.moveTo((viewX + viewWidth / 2) - arrowWidth / 2, balloonY + balloonHeight)
//                    path.lineTo(viewX + viewWidth / 2f, balloonY + balloonHeight + arrowHeight)
//                    path.lineTo((viewX + viewWidth / 2) + arrowWidth / 2, balloonY + balloonHeight)
//                    path.close()
//                }
//                "BOTTOM" -> {
//                    path.moveTo((viewX + viewWidth / 2) - arrowWidth / 2, balloonY)
//                    path.lineTo(viewX + viewWidth / 2f, balloonY - arrowHeight)
//                    path.lineTo((viewX + viewWidth / 2) + arrowWidth / 2, balloonY)
//                    path.close()
//                }
//                "LEFT" -> {
//                    path.moveTo(balloonX + balloonWidth, (viewY + viewHeight / 2) - arrowWidth / 2)
//                    path.lineTo(balloonX + balloonWidth + arrowHeight, viewY + viewHeight / 2f)
//                    path.lineTo(balloonX + balloonWidth, (viewY + viewHeight / 2) + arrowWidth / 2)
//                    path.close()
//                }
//                "RIGHT" -> {
//                    path.moveTo(balloonX, (viewY + viewHeight / 2) - arrowWidth / 2)
//                    path.lineTo(balloonX - arrowHeight, viewY + viewHeight / 2f)
//                    path.lineTo(balloonX, (viewY + viewHeight / 2) + arrowWidth / 2)
//                    path.close()
//                }
//            }
//
//            canvas.drawPath(path, balloonPaint)
//
//            // Desenhar texto
//            val textStartY = balloonY + padding - textPaint.ascent()
//
//            // Desenhe cada linha de texto
//            var currentTextY = textStartY
//            for (line in lines) {
//                val textX = balloonX + padding
//                canvas.drawText(line, textX, currentTextY, textPaint)
//                currentTextY += textHeight + 5f
//            }
//        }
//        binding.ivOverlayWithHole.setImageBitmap(overlay)
//    }

    // Função auxiliar para obter a altura da barra de status e usar como ponto de referência
    fun statusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

//    private fun showDialog() {
//        val dialog = OverlayDialogFragment()
//        dialog.onShowButtonClick = {
//            //drawHighlight(binding.ivOverlayWithHole, "Aqui um texto informando no que tem de novidade.")
//        }
//        dialog.show(childFragmentManager, "DIALOG")
//    }


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