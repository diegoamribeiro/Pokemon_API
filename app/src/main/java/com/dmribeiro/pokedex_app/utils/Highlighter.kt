package com.dmribeiro.pokedex_app.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow

class Highlighter(private val context: Context) {

    private var popupWindow: PopupWindow? = null

    fun drawHighlight(viewToHighlight: View, tooltipText: String) {
        draw(viewToHighlight, tooltipText)
    }

    private fun draw(view: View, tooltipText: String) {
        val popupView = object : View(view.context) {
            override fun onDraw(canvas: Canvas) {
                super.onDraw(canvas)
                canvas.drawColor(Color.parseColor(OVERLAY_COLOR_ALPHA))

                val clearPaint = Paint().apply {
                    xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                    style = Paint.Style.FILL
                }

                val coordinates = IntArray(2)
                view.getLocationOnScreen(coordinates)
                val statusBarHeight: Int = getStatusBarHeight()
                val viewX = coordinates[0].toFloat()
                val viewY = coordinates[1].toFloat() - statusBarHeight
                val viewWidth = view.width.toFloat()
                val viewHeight = view.height.toFloat()
                val displayMetrics = Resources.getSystem().displayMetrics
                val screenHeight = displayMetrics.heightPixels
                val screenWidth = displayMetrics.widthPixels

                // Desenha o retângulo transparente
                canvas.drawRect(
                    viewX - RECTANGLE_PADDING,
                    viewY - RECTANGLE_PADDING,
                    viewX + viewWidth + RECTANGLE_PADDING,
                    viewY + viewHeight + RECTANGLE_PADDING,
                    clearPaint
                )

                val rect = RectF(viewX, viewY, viewX + viewWidth, viewY + viewHeight)

                // Desenha tooltip caso haja texto
                if (tooltipText.isNotEmpty()) {
                    val textPaint = Paint().apply {
                        color = Color.WHITE
                        textSize = TEXT_SIZE_MULTIPLIER * context.resources.displayMetrics.scaledDensity
                        isAntiAlias = true
                    }

                    val lines = tooltipText.split("\n")
                    val textHeight = textPaint.textSize
                    val maxWidth = lines.maxOf { textPaint.measureText(it) }
                    val balloonWidth = maxWidth + 2 * TEXT_PADDING
                    val totalTextHeight = lines.size * textHeight + (lines.size - 1) * LINE_SPACING

                    val balloonHeight = totalTextHeight + 2 * TEXT_PADDING

                    var balloonX = (rect.left + rect.width() / 2) - balloonWidth / 2
                    var balloonY = rect.top - balloonHeight - TEXT_PADDING - ARROW_HEIGHT

                    var arrowDirection = ARROW_DIRECTION_TOP

                    when {
                        balloonY - MARGIN >= 0 -> { /* Mantém a posição atual (acima da View) */
                        }

                        balloonY + balloonHeight + rect.height() + 2 * TEXT_PADDING + ARROW_HEIGHT + MARGIN <= screenHeight -> {
                            balloonY = rect.bottom + TEXT_PADDING
                            arrowDirection = ARROW_DIRECTION_BOTTOM
                        }

                        rect.right + balloonWidth + TEXT_PADDING + MARGIN <= screenWidth -> {
                            balloonX = rect.right + TEXT_PADDING
                            balloonY = rect.top + (rect.height() - balloonHeight) / 2
                            arrowDirection = ARROW_DIRECTION_RIGHT
                        }

                        rect.left - balloonWidth - TEXT_PADDING - MARGIN >= 0 -> {
                            balloonX = rect.left - balloonWidth - TEXT_PADDING
                            balloonY = rect.top + (rect.height() - balloonHeight) / 2
                            arrowDirection = ARROW_DIRECTION_LEFT
                        }
                    }

                    balloonX = balloonX.coerceAtLeast(MARGIN)
                        .coerceAtMost(screenWidth - balloonWidth - MARGIN)
                    balloonY = balloonY.coerceAtLeast(MARGIN)
                        .coerceAtMost(screenHeight - balloonHeight - MARGIN)

                    val rectF =
                        RectF(balloonX, balloonY, balloonX + balloonWidth, balloonY + balloonHeight)
                    val balloonPaint = Paint().apply {
                        color = Color.BLACK
                        style = Paint.Style.FILL
                        isAntiAlias = true
                    }
                    canvas.drawRoundRect(rectF, BALLOON_CORNER_RADIUS, BALLOON_CORNER_RADIUS, balloonPaint)

                    // Aqui é feito o posicionamento do balão referente à view e a tela
                    val path = Path()
                    when (arrowDirection) {
                        ARROW_DIRECTION_TOP -> {
                            path.moveTo(
                                (rect.left + rect.width() / 2) - ARROW_WIDTH / 2,
                                balloonY + balloonHeight
                            )
                            path.lineTo(
                                rect.left + rect.width() / 2f,
                                balloonY + balloonHeight + ARROW_HEIGHT
                            )
                            path.lineTo(
                                (rect.left + rect.width() / 2) + ARROW_WIDTH / 2,
                                balloonY + balloonHeight
                            )
                            path.close()
                        }

                        ARROW_DIRECTION_BOTTOM -> {
                            path.moveTo((rect.left + rect.width() / 2) - ARROW_WIDTH / 2, balloonY)
                            path.lineTo(rect.left + rect.width() / 2f, balloonY - ARROW_HEIGHT)
                            path.lineTo((rect.left + rect.width() / 2) + ARROW_WIDTH / 2, balloonY)
                            path.close()
                        }

                        ARROW_DIRECTION_LEFT -> {
                            path.moveTo(
                                balloonX + balloonWidth,
                                (rect.top + rect.height() / 2) - ARROW_WIDTH / 2
                            )
                            path.lineTo(
                                balloonX + balloonWidth + ARROW_HEIGHT,
                                rect.top + rect.height() / 2f
                            )
                            path.lineTo(
                                balloonX + balloonWidth,
                                (rect.top + rect.height() / 2) + ARROW_WIDTH / 2
                            )
                            path.close()
                        }

                        ARROW_DIRECTION_RIGHT -> {
                            path.moveTo(balloonX, (rect.top + rect.height() / 2) - ARROW_WIDTH / 2)
                            path.lineTo(balloonX - ARROW_HEIGHT, rect.top + rect.height() / 2f)
                            path.lineTo(balloonX, (rect.top + rect.height() / 2) + ARROW_WIDTH / 2)
                            path.close()
                        }
                    }

                    canvas.drawPath(path, balloonPaint)

                    val textStartY = balloonY + TEXT_PADDING - textPaint.ascent()
                    var currentTextY = textStartY
                    for (line in lines) {
                        val textWidth = textPaint.measureText(line)
                        val textX = balloonX + (balloonWidth - textWidth) / 2
                        canvas.drawText(line, textX, currentTextY, textPaint)
                        currentTextY += textHeight + LINE_SPACING
                    }
                }
            }
        }

        // O highlight some quando clicado
        popupView.setOnClickListener {
            popupWindow?.dismiss()
            popupWindow = null
        }

        popupWindow?.dismiss()
        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            false
        )

        popupWindow?.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0)
    }


    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier(
            STATUS_BAR_HEIGHT_RESOURCE_NAME,
            DIMEN_RESOURCE_TYPE, ANDROID_PACKAGE_NAME
        )
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun dismiss() {
        popupWindow?.dismiss()
    }

    companion object {
        const val ARROW_DIRECTION_TOP = "TOP"
        const val ARROW_DIRECTION_BOTTOM = "BOTTOM"
        const val ARROW_DIRECTION_LEFT = "LEFT"
        const val ARROW_DIRECTION_RIGHT = "RIGHT"
        const val STATUS_BAR_HEIGHT_RESOURCE_NAME = "status_bar_height"
        const val DIMEN_RESOURCE_TYPE = "dimen"
        const val ANDROID_PACKAGE_NAME = "android"
        private const val OVERLAY_COLOR_ALPHA = "#90000000"
        private const val RECTANGLE_PADDING = 10f
        private const val MARGIN = 10f
        private const val ARROW_HEIGHT = 15f
        private const val ARROW_WIDTH = 20f
        private const val TEXT_SIZE_MULTIPLIER = 12f
        private const val TEXT_PADDING = 20f
        private const val LINE_SPACING = 5f
        private const val BALLOON_CORNER_RADIUS = 15f
    }
}