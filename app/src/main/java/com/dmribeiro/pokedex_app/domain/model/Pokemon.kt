package com.dmribeiro.pokedex_app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmribeiro.pokedex_app.data.remote.model.*
import com.dmribeiro.pokedex_app.utils.Constants.POKEMON_TABLE
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Pokemon(

    @PrimaryKey(autoGenerate = false)
    val number: Int,
    val abilities: @RawValue List<Abilities>?,
    val baseExperience: Int?,
    val weight: Int?,
    val height: Int?,
    val name: String,
    val stats: @RawValue List<Stats>?,
    val types: @RawValue List<Types>,
    val imageUrl: @RawValue String?,
    val speciesResponse: @RawValue Species?
): Parcelable


