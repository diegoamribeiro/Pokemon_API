package com.dmribeiro.pokedex_app.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dmribeiro.pokedex_app.databinding.ItemPokemonBinding
import com.dmribeiro.pokedex_app.domain.model.Pokemon
import com.dmribeiro.pokedex_app.presentation.view.fragments.home.HomeFragmentDirections
import com.dmribeiro.pokedex_app.utils.DiffUtilGeneric
import com.dmribeiro.pokedex_app.utils.gone
import com.dmribeiro.pokedex_app.utils.setTypeLightColor
import java.util.*

class PokemonHomeAdapter : RecyclerView.Adapter<PokemonHomeAdapter.HomeViewHolder>() {

    private var pokemonList = emptyList<Pokemon>()
    
    class HomeViewHolder(val binding: ItemPokemonBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.apply {

            if (pokemonList[position].types.size > 1 ){
                mtvTypeNd.text = pokemonList[position].types[1].name.replaceFirstChar { it.uppercase(Locale.getDefault()) }
            }else{
                mtvTypeNd.gone()
            }

            mtvName.text = pokemonList[position].name.replaceFirstChar { it.uppercase(Locale.getDefault()) }
            mtvTypeSt.text = pokemonList[position].types[0].name.replaceFirstChar { it.uppercase(Locale.getDefault()) }
            mtvNumber.text = "#${pokemonList[position].number.toString().padStart(3,'0')}"
            cardViewType.setTypeLightColor(pokemonList[position].types[0].name)

            Glide.with(holder.itemView)
                .load(pokemonList[position].imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivPokemon)
        }

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(pokemonList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(list: List<Pokemon>){
        val pokemonDiffUtil = DiffUtilGeneric(pokemonList, list)
        val pokemonResult = DiffUtil.calculateDiff(pokemonDiffUtil)
        this.pokemonList = list
        pokemonResult.dispatchUpdatesTo(this)
    }
}