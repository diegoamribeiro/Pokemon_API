package com.dmribeiro.pokedex_app.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmribeiro.pokedex_app.model.Abilities
import com.dmribeiro.pokedex_app.model.Stats
import com.dmribeiro.pokedex_app.model.Types
import com.dmribeiro.pokedex_app.utils.Constants.POKEMON_TABLE
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
@Entity(tableName = POKEMON_TABLE)
data class Pokemon(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val number: Int,
    @SerializedName("abilities")
    val abilities: @RawValue List<Abilities>?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("name")
    val name: String,
    @SerializedName("stats")
    val stats: @RawValue List<Stats>?,
    @SerializedName("types")
    val types: @RawValue List<Types>,
    @SerializedName("sprites")
    val imageUrl: String
): Parcelable


