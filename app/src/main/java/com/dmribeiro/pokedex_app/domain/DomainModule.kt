package com.dmribeiro.pokedex_app.domain

import com.dmribeiro.pokedex_app.di.local.LocalDataSource
import com.dmribeiro.pokedex_app.repository.RemoteDatasource
import com.dmribeiro.pokedex_app.repository.Repository
import com.dmribeiro.pokedex_app.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun providesPokemonRepository(
        remoteDatasource: RemoteDatasource,
        localDataSource: LocalDataSource
    ): Repository{
        return RepositoryImpl(remoteDatasource, localDataSource)
    }

}