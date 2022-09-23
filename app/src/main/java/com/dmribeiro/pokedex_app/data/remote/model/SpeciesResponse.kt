package com.dmribeiro.pokedex_app.data.remote.model


import com.google.gson.annotations.SerializedName

data class SpeciesResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)