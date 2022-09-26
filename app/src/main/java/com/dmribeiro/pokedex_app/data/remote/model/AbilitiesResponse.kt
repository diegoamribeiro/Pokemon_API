package com.dmribeiro.pokedex_app.data.remote.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AbilitiesResponse(
    @SerializedName("ability")
    val abilityResponse: AbilityResponse,
    @SerializedName("slot")
    val slot: Int
)