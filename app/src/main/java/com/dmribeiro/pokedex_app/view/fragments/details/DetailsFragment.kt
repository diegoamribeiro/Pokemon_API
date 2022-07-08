package com.dmribeiro.pokedex_app.view.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dmribeiro.pokedex_app.MainActivity
import com.dmribeiro.pokedex_app.R
import com.dmribeiro.pokedex_app.databinding.FragmentDetailsBinding
import com.dmribeiro.pokedex_app.domain.Pokemon
import com.dmribeiro.pokedex_app.utils.Constants.IMAGE_URL
import com.dmribeiro.pokedex_app.utils.setTypeColor
import com.dmribeiro.pokedex_app.utils.setTypeSymbol
import java.util.*
import com.dmribeiro.pokedex_app.utils.setTypeBackground


class DetailsFragment : Fragment(), PokemonListener {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var rotateAnimation: Animation
    private lateinit var menuItem: MenuItem

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.white_100_percent_opacity))

        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(setTypeBackground(args.currentPokemon.types[0].type.name))
        setupViews()
        setArguments()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setArguments(){
        val bundle = Bundle()
        val viewPagerFragment = ViewPagerFragment(this)
        val childFragTrans: FragmentTransaction = childFragmentManager.beginTransaction()
        childFragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        val pokemon = args.currentPokemon
        bundle.putParcelable(POKEMON_NAME, pokemon)
        viewPagerFragment.arguments = bundle
        childFragTrans.replace(R.id.containerDetails, viewPagerFragment)
        childFragTrans.commit()
    }

    private fun ImageView.rotateAnimation(){
        rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        this.startAnimation(rotateAnimation)
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews(){
        binding.constraintParent.setTypeColor(args.currentPokemon.types[0].type.name)

        binding.mtvCurrentPokemon.text = args.currentPokemon.name.replaceFirstChar { it.uppercase(Locale.getDefault()) }
        binding.mtvCurrentNumber.text = "#"+args.currentPokemon.number.toString().padStart(3, '0')
        binding.ivPokeball.rotateAnimation()

        Glide.with(binding.ivCurrentPokemon)
            .load("${IMAGE_URL}${args.currentPokemon.number}.png")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivCurrentPokemon)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        menuItem = menu.findItem(R.id.save_to_favorites)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object{
        const val POKEMON_NAME = "pokemon_name"
    }

    override fun callPokemon(): Pokemon {
        return args.currentPokemon
    }

}