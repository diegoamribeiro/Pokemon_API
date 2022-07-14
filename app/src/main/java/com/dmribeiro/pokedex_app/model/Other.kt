package com.dmribeiro.pokedex_app.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class Other(
    @SerializedName("official-artwork")
    val officialArt: OfficialArtwork
)