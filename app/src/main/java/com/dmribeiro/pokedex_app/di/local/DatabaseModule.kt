package com.dmribeiro.pokedex_app.di.local

import android.content.Context
import androidx.room.Room
import com.dmribeiro.pokedex_app.database.PokemonDao
import com.dmribeiro.pokedex_app.database.PokemonDatabase
import com.dmribeiro.pokedex_app.utils.Constants.POKEMON_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PokemonDatabase::class.java, POKEMON_DATABASE).build()

    @Provides
    @Singleton
    fun providesDao(database: PokemonDatabase) : PokemonDao {
        return database.pokemonDao()
    }

}