package com.dmribeiro.pokedex_app.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.dmribeiro.pokedex_app.R
import com.google.android.material.textview.MaterialTextView

fun View.setTypeLightColor(type: String){
    when(type){
        IndexType.GRASS.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.grass_light_color, resources.newTheme()))
        }
        IndexType.BUG.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.bug_light_color, resources.newTheme()))
        }
        IndexType.DARK.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.dark_light_color, resources.newTheme()))
        }
        IndexType.DRAGON.typeName->{
            this.setBackgroundColor(resources.getColor(R.color.dragon_light_color, resources.newTheme()))
        }
        IndexType.ELECTRIC.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.electric_light_color, resources.newTheme()))
        }
        IndexType.FAIRY.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fairy_light_color, resources.newTheme()))
        }
        IndexType.FIGHTING.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fighting_light_color, resources.newTheme()))
        }
        IndexType.FIRE.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fire_light_color, resources.newTheme()))
        }
        IndexType.NORMAL.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.normal_light_color, resources.newTheme()))
        }
        IndexType.WATER.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.water_light_color, resources.newTheme()))
        }
        IndexType.GHOST.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ghost_light_color, resources.newTheme()))
        }
        IndexType.ICE.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ice_light_color, resources.newTheme()))
        }
        IndexType.PSYCHIC.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.psychic_light_color, resources.newTheme()))
        }
        IndexType.ROCK.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.rock_light_color, resources.newTheme()))
        }
        IndexType.STEEL.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.steel_light_color, resources.newTheme()))
        }
        IndexType.FLYING.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.flying_light_color, resources.newTheme()))
        }
        IndexType.GROUND.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ground_light_color, resources.newTheme()))
        }
        IndexType.POISON.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.poison_light_color, resources.newTheme()))
        }
    }
}

fun View.setTypeColor(type: String){
    when(type){
        IndexType.GRASS.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.grass_color, resources.newTheme()))
        }
        IndexType.BUG.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.bug_color, resources.newTheme()))
        }
        IndexType.DARK.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.dark_color, resources.newTheme()))
        }
        IndexType.DRAGON.typeName->{
            this.setBackgroundColor(resources.getColor(R.color.dragon_color, resources.newTheme()))
        }
        IndexType.ELECTRIC.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.electric_color, resources.newTheme()))
        }
        IndexType.FAIRY.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fairy_color, resources.newTheme()))
        }
        IndexType.FIGHTING.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fighting_color, resources.newTheme()))
        }
        IndexType.FIRE.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fire_color, resources.newTheme()))
        }
        IndexType.NORMAL.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.normal_color, resources.newTheme()))
        }
        IndexType.WATER.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.water_color, resources.newTheme()))
        }
        IndexType.GHOST.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ghost_color, resources.newTheme()))
        }
        IndexType.ICE.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ice_color, resources.newTheme()))
        }
        IndexType.PSYCHIC.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.psychic_color, resources.newTheme()))
        }
        IndexType.ROCK.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.rock_color, resources.newTheme()))
        }
        IndexType.STEEL.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.steel_color, resources.newTheme()))
        }
        IndexType.FLYING.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.flying_color, resources.newTheme()))
        }
        IndexType.GROUND.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ground_color, resources.newTheme()))
        }
        IndexType.POISON.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.poison_color, resources.newTheme()))
        }
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.setTypeSymbol(type: String){
    when(type){
        IndexType.GRASS.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.grass, resources.newTheme()))
        }
        IndexType.BUG.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.bug, resources.newTheme()))
        }
        IndexType.DARK.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.dark, resources.newTheme()))
        }
        IndexType.DRAGON.typeName->{
            this.setImageDrawable(resources.getDrawable(R.drawable.dragon, resources.newTheme()))
        }
        IndexType.ELECTRIC.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.electric, resources.newTheme()))
        }
        IndexType.FAIRY.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.grass, resources.newTheme()))
        }
        IndexType.FIGHTING.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.fighting, resources.newTheme()))
        }
        IndexType.FIRE.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.fire, resources.newTheme()))
        }
        IndexType.NORMAL.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.normal, resources.newTheme()))
        }
        IndexType.WATER.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.water, resources.newTheme()))
        }
        IndexType.GHOST.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.ghost, resources.newTheme()))
        }
        IndexType.ICE.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.ice, resources.newTheme()))
        }
        IndexType.PSYCHIC.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.psychic, resources.newTheme()))
        }
        IndexType.ROCK.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.rock, resources.newTheme()))
        }
        IndexType.STEEL.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.steel, resources.newTheme()))
        }
        IndexType.FLYING.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.flying, resources.newTheme()))
        }
        IndexType.GROUND.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.ground, resources.newTheme()))
        }
        IndexType.POISON.typeName ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.poison, resources.newTheme()))
        }
    }
}

fun setTypeBackground(type: String) : Int{
    when(type){
        IndexType.GRASS.typeName ->{
            return R.color.grass_color
        }
        IndexType.BUG.typeName ->{
            return R.color.bug_color
        }
        IndexType.DARK.typeName ->{
            return R.color.dark_color
        }
        IndexType.DRAGON.typeName->{
            return R.color.dragon_color
        }
        IndexType.ELECTRIC.typeName ->{
            return R.color.electric_color
        }
        IndexType.FAIRY.typeName ->{
            return R.color.fairy_color
        }
        IndexType.FIGHTING.typeName ->{
            return R.color.fighting_color
        }
        IndexType.FIRE.typeName ->{
            return R.color.fire_color
        }
        IndexType.NORMAL.typeName ->{
            return R.color.normal_color
        }
        IndexType.WATER.typeName ->{
            return R.color.water_color
        }
        IndexType.GHOST.typeName ->{
            return R.color.ghost_color
        }
        IndexType.ICE.typeName ->{
            return R.color.ice_color
        }
        IndexType.PSYCHIC.typeName ->{
            return R.color.psychic_color
        }
        IndexType.ROCK.typeName ->{
            return R.color.rock_color
        }
        IndexType.STEEL.typeName ->{
            return R.color.steel_color
        }
        IndexType.FLYING.typeName ->{
            return R.color.flying_color
        }
        IndexType.GROUND.typeName ->{
            return R.color.ground_color
        }
        IndexType.POISON.typeName ->{
            return R.color.poison_color
        }else->{
        return R.color.normal_color
        }
    }
}

fun setTypeBackgroundTint(type: String) : String{
    when(type){
        IndexType.BUG.typeName ->{
            return "#A8B820"
        }
        IndexType.DARK.typeName ->{
            return "#705848"
        }
        IndexType.DRAGON.typeName ->{
            return "#7038F8"
        }
        IndexType.ELECTRIC.typeName ->{
            return "#F8D030"
        }
        IndexType.FAIRY.typeName ->{
            return "#EE99AC"
        }
        IndexType.FIGHTING.typeName ->{
            return "#C03028"
        }
        IndexType.FIRE.typeName ->{
            return "#F5AC78"
        }
        IndexType.GROUND.typeName ->{
            return "#E0C068"
        }
        IndexType.FLYING.typeName ->{
            return "#A890F0"
        }
        IndexType.GHOST.typeName ->{
            return "#705898"
        }
        IndexType.GRASS.typeName ->{
            return "#A7DB8D"
        }
        IndexType.ICE.typeName ->{
            return "#98D8D8"
        }
        IndexType.NORMAL.typeName ->{
            return "#A8A878"
        }
        IndexType.POISON.typeName ->{
            return "#A040A0"
        }
        IndexType.PSYCHIC.typeName ->{
            return "#F85888"
        }
        IndexType.ROCK.typeName ->{
            return "#B8A038"
        }
        IndexType.STEEL.typeName ->{
            return "#B8B8D0"
        }
        IndexType.WATER.typeName ->{
            return "#5BC7E5"
        }
        else->{
        return "#A8A878"
    }
    }
}

fun Int.heightFormat() : String{
    val newHeight = this.toString()
    val formatted: String = if (newHeight.length >=2){
        "${newHeight.substring(0, 1)}.${newHeight.substring(1, newHeight.length)}"
    }else{
        "0.${newHeight}"
    }
    return formatted
}

fun Int.convertPoundsToKilogram() : String{
    val kilogram = this.times(0.454)
    return kilogram.toInt().toString() + "Kg"
}

fun MaterialTextView.setTextTypeLightColor(type: String){
    when(type){
        IndexType.GRASS.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.grass_light_color, resources.newTheme()))
        }
        IndexType.BUG.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.bug_light_color, resources.newTheme()))
        }
        IndexType.DARK.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.dark_light_color, resources.newTheme()))
        }
        IndexType.DRAGON.typeName->{
            this.setBackgroundColor(resources.getColor(R.color.dragon_light_color, resources.newTheme()))
        }
        IndexType.ELECTRIC.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.electric_light_color, resources.newTheme()))
        }
        IndexType.FAIRY.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fairy_light_color, resources.newTheme()))
        }
        IndexType.FIGHTING.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fighting_light_color, resources.newTheme()))
        }
        IndexType.FIRE.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.fire_light_color, resources.newTheme()))
        }
        IndexType.NORMAL.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.normal_light_color, resources.newTheme()))
        }
        IndexType.WATER.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.water_light_color, resources.newTheme()))
        }
        IndexType.GHOST.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ghost_light_color, resources.newTheme()))
        }
        IndexType.ICE.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ice_light_color, resources.newTheme()))
        }
        IndexType.PSYCHIC.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.psychic_light_color, resources.newTheme()))
        }
        IndexType.ROCK.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.rock_light_color, resources.newTheme()))
        }
        IndexType.STEEL.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.steel_light_color, resources.newTheme()))
        }
        IndexType.FLYING.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.flying_light_color, resources.newTheme()))
        }
        IndexType.GROUND.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.ground_light_color, resources.newTheme()))
        }
        IndexType.POISON.typeName ->{
            this.setBackgroundColor(resources.getColor(R.color.poison_light_color, resources.newTheme()))
        }
    }
}



