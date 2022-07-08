package com.dmribeiro.pokedex_app.model


import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)