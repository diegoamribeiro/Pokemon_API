package com.dmribeiro.pokedex_app.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Abilities(
    @SerializedName("ability")
    val ability: Ability,
    @SerializedName("slot")
    val slot: Int
)