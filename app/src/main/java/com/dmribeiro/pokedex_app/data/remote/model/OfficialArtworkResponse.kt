package com.dmribeiro.pokedex_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class OfficialArtworkResponse(
    @SerializedName("front_default")
    val frontDefault: String
)