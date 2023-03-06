package com.dmribeiro.pokedex_app.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.dmribeiro.pokedex_app.R
import com.google.android.material.textview.MaterialTextView

fun View.setTypeLightColor(type: String){
    when(type){
        IndexType.GRASS.type ->{
            this.setBackgroundColor(resources.getColor(R.color.grass_light_color, resources.newTheme()))
        }
        IndexType.BUG.type ->{
            this.setBackgroundColor(resources.getColor(R.color.bug_light_color, resources.newTheme()))
        }
        IndexType.DARK.type ->{
            this.setBackgroundColor(resources.getColor(R.color.dark_light_color, resources.newTheme()))
        }
        IndexType.DRAGON.type->{
            this.setBackgroundColor(resources.getColor(R.color.dragon_light_color, resources.newTheme()))
        }
        IndexType.ELECTRIC.type ->{
            this.setBackgroundColor(resources.getColor(R.color.electric_light_color, resources.newTheme()))
        }
        IndexType.FAIRY.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fairy_light_color, resources.newTheme()))
        }
        IndexType.FIGHTING.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fighting_light_color, resources.newTheme()))
        }
        IndexType.FIRE.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fire_light_color, resources.newTheme()))
        }
        IndexType.NORMAL.type ->{
            this.setBackgroundColor(resources.getColor(R.color.normal_light_color, resources.newTheme()))
        }
        IndexType.WATER.type ->{
            this.setBackgroundColor(resources.getColor(R.color.water_light_color, resources.newTheme()))
        }
        IndexType.GHOST.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ghost_light_color, resources.newTheme()))
        }
        IndexType.ICE.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ice_light_color, resources.newTheme()))
        }
        IndexType.PSYCHIC.type ->{
            this.setBackgroundColor(resources.getColor(R.color.psychic_light_color, resources.newTheme()))
        }
        IndexType.ROCK.type ->{
            this.setBackgroundColor(resources.getColor(R.color.rock_light_color, resources.newTheme()))
        }
        IndexType.STEEL.type ->{
            this.setBackgroundColor(resources.getColor(R.color.steel_light_color, resources.newTheme()))
        }
        IndexType.FLYING.type ->{
            this.setBackgroundColor(resources.getColor(R.color.flying_light_color, resources.newTheme()))
        }
        IndexType.GROUND.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ground_light_color, resources.newTheme()))
        }
        IndexType.POISON.type ->{
            this.setBackgroundColor(resources.getColor(R.color.poison_light_color, resources.newTheme()))
        }
    }
}

fun View.setTypeColor(type: String){
    when(type){
        IndexType.GRASS.type ->{
            this.setBackgroundColor(resources.getColor(R.color.grass_color, resources.newTheme()))
        }
        IndexType.BUG.type ->{
            this.setBackgroundColor(resources.getColor(R.color.bug_color, resources.newTheme()))
        }
        IndexType.DARK.type ->{
            this.setBackgroundColor(resources.getColor(R.color.dark_color, resources.newTheme()))
        }
        IndexType.DRAGON.type->{
            this.setBackgroundColor(resources.getColor(R.color.dragon_color, resources.newTheme()))
        }
        IndexType.ELECTRIC.type ->{
            this.setBackgroundColor(resources.getColor(R.color.electric_color, resources.newTheme()))
        }
        IndexType.FAIRY.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fairy_color, resources.newTheme()))
        }
        IndexType.FIGHTING.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fighting_color, resources.newTheme()))
        }
        IndexType.FIRE.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fire_color, resources.newTheme()))
        }
        IndexType.NORMAL.type ->{
            this.setBackgroundColor(resources.getColor(R.color.normal_color, resources.newTheme()))
        }
        IndexType.WATER.type ->{
            this.setBackgroundColor(resources.getColor(R.color.water_color, resources.newTheme()))
        }
        IndexType.GHOST.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ghost_color, resources.newTheme()))
        }
        IndexType.ICE.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ice_color, resources.newTheme()))
        }
        IndexType.PSYCHIC.type ->{
            this.setBackgroundColor(resources.getColor(R.color.psychic_color, resources.newTheme()))
        }
        IndexType.ROCK.type ->{
            this.setBackgroundColor(resources.getColor(R.color.rock_color, resources.newTheme()))
        }
        IndexType.STEEL.type ->{
            this.setBackgroundColor(resources.getColor(R.color.steel_color, resources.newTheme()))
        }
        IndexType.FLYING.type ->{
            this.setBackgroundColor(resources.getColor(R.color.flying_color, resources.newTheme()))
        }
        IndexType.GROUND.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ground_color, resources.newTheme()))
        }
        IndexType.POISON.type ->{
            this.setBackgroundColor(resources.getColor(R.color.poison_color, resources.newTheme()))
        }
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.setTypeSymbol(type: String){
    when(type){
        IndexType.GRASS.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.grass, resources.newTheme()))
        }
        IndexType.BUG.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.bug, resources.newTheme()))
        }
        IndexType.DARK.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.dark, resources.newTheme()))
        }
        IndexType.DRAGON.type->{
            this.setImageDrawable(resources.getDrawable(R.drawable.dragon, resources.newTheme()))
        }
        IndexType.ELECTRIC.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.electric, resources.newTheme()))
        }
        IndexType.FAIRY.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.fairy, resources.newTheme()))
        }
        IndexType.FIGHTING.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.fighting, resources.newTheme()))
        }
        IndexType.FIRE.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.fire, resources.newTheme()))
        }
        IndexType.NORMAL.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.normal, resources.newTheme()))
        }
        IndexType.WATER.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.water, resources.newTheme()))
        }
        IndexType.GHOST.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.ghost, resources.newTheme()))
        }
        IndexType.ICE.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.ice, resources.newTheme()))
        }
        IndexType.PSYCHIC.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.psychic, resources.newTheme()))
        }
        IndexType.ROCK.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.rock, resources.newTheme()))
        }
        IndexType.STEEL.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.steel, resources.newTheme()))
        }
        IndexType.FLYING.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.flying, resources.newTheme()))
        }
        IndexType.GROUND.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.ground, resources.newTheme()))
        }
        IndexType.POISON.type ->{
            this.setImageDrawable(resources.getDrawable(R.drawable.poison, resources.newTheme()))
        }
    }
}

fun setTypeBackground(type: String) : Int{
    when(type){
        IndexType.GRASS.type ->{
            return R.color.grass_color
        }
        IndexType.BUG.type ->{
            return R.color.bug_color
        }
        IndexType.DARK.type ->{
            return R.color.dark_color
        }
        IndexType.DRAGON.type->{
            return R.color.dragon_color
        }
        IndexType.ELECTRIC.type ->{
            return R.color.electric_color
        }
        IndexType.FAIRY.type ->{
            return R.color.fairy_color
        }
        IndexType.FIGHTING.type ->{
            return R.color.fighting_color
        }
        IndexType.FIRE.type ->{
            return R.color.fire_color
        }
        IndexType.NORMAL.type ->{
            return R.color.normal_color
        }
        IndexType.WATER.type ->{
            return R.color.water_color
        }
        IndexType.GHOST.type ->{
            return R.color.ghost_color
        }
        IndexType.ICE.type ->{
            return R.color.ice_color
        }
        IndexType.PSYCHIC.type ->{
            return R.color.psychic_color
        }
        IndexType.ROCK.type ->{
            return R.color.rock_color
        }
        IndexType.STEEL.type ->{
            return R.color.steel_color
        }
        IndexType.FLYING.type ->{
            return R.color.flying_color
        }
        IndexType.GROUND.type ->{
            return R.color.ground_color
        }
        IndexType.POISON.type ->{
            return R.color.poison_color
        }else->{
        return R.color.normal_color
        }
    }
}

fun setTypeBackgroundDark(type: String) : Int{
    when(type){
        IndexType.GRASS.type ->{
            return R.color.grass_dark_color
        }
        IndexType.BUG.type ->{
            return R.color.bug_dark_color
        }
        IndexType.DARK.type ->{
            return R.color.dark_dark_color
        }
        IndexType.DRAGON.type->{
            return R.color.dragon_dark_color
        }
        IndexType.ELECTRIC.type ->{
            return R.color.electric_dark_color
        }
        IndexType.FAIRY.type ->{
            return R.color.fairy_dark_color
        }
        IndexType.FIGHTING.type ->{
            return R.color.fighting_dark_color
        }
        IndexType.FIRE.type ->{
            return R.color.fire_dark_color
        }
        IndexType.NORMAL.type ->{
            return R.color.normal_dark_color
        }
        IndexType.WATER.type ->{
            return R.color.water_dark_color
        }
        IndexType.GHOST.type ->{
            return R.color.ghost_dark_color
        }
        IndexType.ICE.type ->{
            return R.color.ice_dark_color
        }
        IndexType.PSYCHIC.type ->{
            return R.color.psychic_dark_color
        }
        IndexType.ROCK.type ->{
            return R.color.rock_dark_color
        }
        IndexType.STEEL.type ->{
            return R.color.steel_dark_color
        }
        IndexType.FLYING.type ->{
            return R.color.flying_dark_color
        }
        IndexType.GROUND.type ->{
            return R.color.ground_dark_color
        }
        IndexType.POISON.type ->{
            return R.color.poison_dark_color
        }else->{
        return R.color.normal_color
    }
    }
}

fun setTypeBackgroundTranslucent(type: String) : String{
    when(type){
        IndexType.BUG.type ->{
                return "#80C6D16E"
            }
            IndexType.DARK.type ->{
                return "#80A29288"
            }
            IndexType.DRAGON.type ->{
                return "#80A27DFA"
            }
            IndexType.ELECTRIC.type ->{
                return "#80FAE078"
            }
            IndexType.FAIRY.type ->{
                return "#80F4BDC9"
            }
            IndexType.FIGHTING.type ->{
                return "#80D67873"
            }
            IndexType.FIRE.type ->{
                return "#80F5AC78"
            }
            IndexType.GROUND.type ->{
                return "#80EBD69D"
            }
            IndexType.FLYING.type ->{
                return "#80C6B7F5"
            }
            IndexType.GHOST.type ->{
                return "#80A292BC"
            }
            IndexType.GRASS.type ->{
                return "#80A7DB8D"
            }
            IndexType.ICE.type ->{
                return "#80BCE6E6"
            }
            IndexType.NORMAL.type ->{
                return "#80C6C6A7"
            }
            IndexType.POISON.type ->{
                return "#80C183C1"
            }
            IndexType.PSYCHIC.type ->{
                return "#80FA92B2"
            }
            IndexType.ROCK.type ->{
                return "#80D1C17D"
            }
            IndexType.STEEL.type ->{
                return "#80D1D1E0"
            }
            IndexType.WATER.type ->{
                return "#8094DBEE"
            }
            else-> {
                return "#80C6C6A7"
            }
    }
}

fun setTypeBackgroundTint(type: String) : String{
    when(type){
        IndexType.BUG.type ->{
            return "#A8B820"
        }
        IndexType.DARK.type ->{
            return "#705848"
        }
        IndexType.DRAGON.type ->{
            return "#7038F8"
        }
        IndexType.ELECTRIC.type ->{
            return "#F8D030"
        }
        IndexType.FAIRY.type ->{
            return "#EE99AC"
        }
        IndexType.FIGHTING.type ->{
            return "#C03028"
        }
        IndexType.FIRE.type ->{
            return "#F5AC78"
        }
        IndexType.GROUND.type ->{
            return "#E0C068"
        }
        IndexType.FLYING.type ->{
            return "#A890F0"
        }
        IndexType.GHOST.type ->{
            return "#705898"
        }
        IndexType.GRASS.type ->{
            return "#A7DB8D"
        }
        IndexType.ICE.type ->{
            return "#98D8D8"
        }
        IndexType.NORMAL.type ->{
            return "#A8A878"
        }
        IndexType.POISON.type ->{
            return "#A040A0"
        }
        IndexType.PSYCHIC.type ->{
            return "#F85888"
        }
        IndexType.ROCK.type ->{
            return "#B8A038"
        }
        IndexType.STEEL.type ->{
            return "#B8B8D0"
        }
        IndexType.WATER.type ->{
            return "#5BC7E5"
        }
        else->{
        return "#A8A878"
    }
    }
}

fun setTypeBackgroundLight(type: String) : Int{
    when(type){
        IndexType.GRASS.type ->{
            return R.color.grass_translucent_color
        }
        IndexType.BUG.type ->{
            return R.color.bug_translucent_color
        }
        IndexType.DARK.type ->{
            return R.color.dark_translucent_color
        }
        IndexType.DRAGON.type->{
            return R.color.dragon_translucent_color
        }
        IndexType.ELECTRIC.type ->{
            return R.color.electric_translucent_color
        }
        IndexType.FAIRY.type ->{
            return R.color.fairy_translucent_color
        }
        IndexType.FIGHTING.type ->{
            return R.color.fighting_translucent_color
        }
        IndexType.FIRE.type ->{
            return R.color.fire_translucent_color
        }
        IndexType.NORMAL.type ->{
            return R.color.normal_translucent_color
        }
        IndexType.WATER.type ->{
            return R.color.water_translucent_color
        }
        IndexType.GHOST.type ->{
            return R.color.ghost_translucent_color
        }
        IndexType.ICE.type ->{
            return R.color.ice_translucent_color
        }
        IndexType.PSYCHIC.type ->{
            return R.color.psychic_translucent_color
        }
        IndexType.ROCK.type ->{
            return R.color.rock_translucent_color
        }
        IndexType.STEEL.type ->{
            return R.color.steel_translucent_color
        }
        IndexType.FLYING.type ->{
            return R.color.flying_translucent_color
        }
        IndexType.GROUND.type ->{
            return R.color.ground_translucent_color
        }
        IndexType.POISON.type ->{
            return R.color.poison_translucent_color
        }else->{
        return R.color.normal_translucent_color
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

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun MaterialTextView.setTextTypeLightColor(type: String){
    when(type){
        IndexType.GRASS.type ->{
            this.setBackgroundColor(resources.getColor(R.color.grass_light_color, resources.newTheme()))
        }
        IndexType.BUG.type ->{
            this.setBackgroundColor(resources.getColor(R.color.bug_light_color, resources.newTheme()))
        }
        IndexType.DARK.type ->{
            this.setBackgroundColor(resources.getColor(R.color.dark_light_color, resources.newTheme()))
        }
        IndexType.DRAGON.type->{
            this.setBackgroundColor(resources.getColor(R.color.dragon_light_color, resources.newTheme()))
        }
        IndexType.ELECTRIC.type ->{
            this.setBackgroundColor(resources.getColor(R.color.electric_light_color, resources.newTheme()))
        }
        IndexType.FAIRY.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fairy_light_color, resources.newTheme()))
        }
        IndexType.FIGHTING.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fighting_light_color, resources.newTheme()))
        }
        IndexType.FIRE.type ->{
            this.setBackgroundColor(resources.getColor(R.color.fire_light_color, resources.newTheme()))
        }
        IndexType.NORMAL.type ->{
            this.setBackgroundColor(resources.getColor(R.color.normal_light_color, resources.newTheme()))
        }
        IndexType.WATER.type ->{
            this.setBackgroundColor(resources.getColor(R.color.water_light_color, resources.newTheme()))
        }
        IndexType.GHOST.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ghost_light_color, resources.newTheme()))
        }
        IndexType.ICE.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ice_light_color, resources.newTheme()))
        }
        IndexType.PSYCHIC.type ->{
            this.setBackgroundColor(resources.getColor(R.color.psychic_light_color, resources.newTheme()))
        }
        IndexType.ROCK.type ->{
            this.setBackgroundColor(resources.getColor(R.color.rock_light_color, resources.newTheme()))
        }
        IndexType.STEEL.type ->{
            this.setBackgroundColor(resources.getColor(R.color.steel_light_color, resources.newTheme()))
        }
        IndexType.FLYING.type ->{
            this.setBackgroundColor(resources.getColor(R.color.flying_light_color, resources.newTheme()))
        }
        IndexType.GROUND.type ->{
            this.setBackgroundColor(resources.getColor(R.color.ground_light_color, resources.newTheme()))
        }
        IndexType.POISON.type ->{
            this.setBackgroundColor(resources.getColor(R.color.poison_light_color, resources.newTheme()))
        }
    }
}



