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

    fun highlight(viewToHighlight: View, tooltipText: String) {
        viewToHighlight.drawHighlightDef(tooltipText)
    }

    private fun View.drawHighlightDef(tooltipText: String) {
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

                        var arrowDirection = ARROW_DIRECTION_TOP

                        when {
                            balloonY - margin >= 0 -> { /* Mantém a posição atual (acima da View) */ }

                            balloonY + balloonHeight + rect.height() + 2 * padding + arrowHeight + margin <= screenHeight -> {
                                balloonY = rect.bottom + padding
                                arrowDirection = ARROW_DIRECTION_BOTTOM
                            }

                            rect.right + balloonWidth + padding + margin <= screenWidth -> {
                                balloonX = rect.right + padding
                                balloonY = rect.top + (rect.height() - balloonHeight) / 2
                                arrowDirection = ARROW_DIRECTION_RIGHT
                            }

                            rect.left - balloonWidth - padding - margin >= 0 -> {
                                balloonX = rect.left - balloonWidth - padding
                                balloonY = rect.top + (rect.height() - balloonHeight) / 2
                                arrowDirection = ARROW_DIRECTION_LEFT
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
                        canvas.drawRoundRect(rectF, 15f, 15f, balloonPaint)

                        val path = Path()
                        when (arrowDirection) {
                            ARROW_DIRECTION_TOP -> {
                                path.moveTo((rect.left + rect.width() / 2) - arrowWidth / 2, balloonY + balloonHeight)
                                path.lineTo(rect.left + rect.width() / 2f, balloonY + balloonHeight + arrowHeight)
                                path.lineTo((rect.left + rect.width() / 2) + arrowWidth / 2, balloonY + balloonHeight)
                                path.close()
                            }

                            ARROW_DIRECTION_BOTTOM -> {
                                path.moveTo((rect.left + rect.width() / 2) - arrowWidth / 2, balloonY)
                                path.lineTo(rect.left + rect.width() / 2f, balloonY - arrowHeight)
                                path.lineTo((rect.left + rect.width() / 2) + arrowWidth / 2, balloonY)
                                path.close()
                            }

                            ARROW_DIRECTION_LEFT -> {
                                path.moveTo(balloonX + balloonWidth, (rect.top + rect.height() / 2) - arrowWidth / 2)
                                path.lineTo(balloonX + balloonWidth + arrowHeight, rect.top + rect.height() / 2f)
                                path.lineTo(balloonX + balloonWidth, (rect.top + rect.height() / 2) + arrowWidth / 2)
                                path.close()
                            }

                            ARROW_DIRECTION_RIGHT -> {
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
                            val textWidth = textPaint.measureText(line)
                            val textX = balloonX + (balloonWidth - textWidth) / 2
                            canvas.drawText(line, textX, currentTextY, textPaint)
                            currentTextY += textHeight + 5f
                        }
                    }
                }
            }
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

            popupWindow?.showAtLocation(this, Gravity.NO_GRAVITY, 0, 0)
        }
        draw()
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier(
            STATUS_BAR_HEIGHT_RESOURCE_NAME,
            DIMEN_RESOURCE_TYPE,
            ANDROID_PACKAGE_NAME)
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
    }
}