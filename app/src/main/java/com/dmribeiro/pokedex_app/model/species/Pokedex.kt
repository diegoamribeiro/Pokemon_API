package com.dmribeiro.pokedex_app.model.species


import com.google.gson.annotations.SerializedName

data class Pokedex(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)