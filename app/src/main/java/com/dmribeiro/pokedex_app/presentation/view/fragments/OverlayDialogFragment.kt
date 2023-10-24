package com.dmribeiro.pokedex_app.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dmribeiro.pokedex_app.R
import com.dmribeiro.pokedex_app.databinding.FragmentOverlayDialogBinding
import com.dmribeiro.pokedex_app.presentation.view.fragments.home.HomeFragment
import com.dmribeiro.pokedex_app.utils.viewBinding

class OverlayDialogFragment : DialogFragment() {
    private val binding: FragmentOverlayDialogBinding by viewBinding()
    var onShowButtonClick: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btShow.setOnClickListener {
            onShowButtonClick?.invoke()
            dismiss()
        }
    }
}
