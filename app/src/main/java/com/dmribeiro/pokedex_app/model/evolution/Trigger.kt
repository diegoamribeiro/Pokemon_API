package com.dmribeiro.pokedex_app.model.evolution


import com.google.gson.annotations.SerializedName

data class Trigger(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)