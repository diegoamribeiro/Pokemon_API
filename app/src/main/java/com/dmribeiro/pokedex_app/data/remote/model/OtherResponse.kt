package com.dmribeiro.pokedex_app.data.remote.model

import com.google.gson.annotations.SerializedName


data class OtherResponse(
    @SerializedName("official-artwork")
    val officialArt: OfficialArtworkResponse
)