package com.dmribeiro.pokedex_app.utils

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(T::class.java)
