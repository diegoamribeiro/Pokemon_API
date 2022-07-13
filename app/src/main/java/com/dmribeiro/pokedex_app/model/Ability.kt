package com.dmribeiro.pokedex_app.model


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)