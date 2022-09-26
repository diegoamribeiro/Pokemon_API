package com.dmribeiro.pokedex_app.data.di.local

import android.content.Context
import com.dmribeiro.pokedex_app.data.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext context: Context) =
        UserPreferencesRepository(context)
}