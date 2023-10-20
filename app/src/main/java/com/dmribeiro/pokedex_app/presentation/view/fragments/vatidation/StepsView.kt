package com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.dmribeiro.pokedex_app.databinding.ItemStepBarGrayBinding
import com.dmribeiro.pokedex_app.databinding.ItemStepBarGreenBinding
import com.dmribeiro.pokedex_app.databinding.ItemStepBarTextBinding


class StepsView(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {
    fun createStepsBar(stepCurrent: Int, stepTotal: Int, textCurrent: String = "") {
        val scale = context.resources.displayMetrics.density
        val screenWidth = context.resources.displayMetrics.widthPixels
        val margins = 24f * scale + 0.5f
        val marginBetweenBlocks = (8f * stepTotal) * scale + 0.5f
        val width = screenWidth - margins - marginBetweenBlocks
        val blockSizeInit = width / stepTotal
        val primaryBlockSize = (blockSizeInit * 1.5).toInt()
        val blockSize = ((width - primaryBlockSize) / (stepTotal - 1)).toInt()
        val currentStep = stepCurrent - 1

        for (i in 0 until stepTotal) {
            if (i < currentStep) {
                val bindingGreen = ItemStepBarGreenBinding.inflate(
                    LayoutInflater.from(context), null, false
                )
                bindingGreen.root.getChildAt(0).layoutParams.width = blockSize
                bindingGreen.root.getChildAt(0).requestLayout()
                this.addView(bindingGreen.root)
            } else if (i == currentStep) {
                val bindingText = ItemStepBarTextBinding.inflate(
                    LayoutInflater.from(context), null, false
                )
                bindingText.root.getChildAt(0).layoutParams.height = (30f * scale + 0.5f).toInt()
                bindingText.root.getChildAt(0).layoutParams.width = primaryBlockSize
                bindingText.root.getChildAt(0).requestLayout()
                bindingText.tvLabelStep.text = textCurrent
                this.addView(bindingText.root)
            } else {
                val bindingGray = ItemStepBarGrayBinding.inflate(
                    LayoutInflater.from(context), null, false
                )
                bindingGray.root.getChildAt(0).layoutParams.width = blockSize
                bindingGray.root.getChildAt(0).requestLayout()
                this.addView(bindingGray.root)
            }
        }
    }
}