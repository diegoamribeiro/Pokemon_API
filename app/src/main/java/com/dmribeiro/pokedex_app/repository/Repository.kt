package com.dmribeiro.pokedex_app.repository

import com.dmribeiro.pokedex_app.di.local.LocalDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDatasource: RemoteDatasource,
    localDataSource: LocalDataSource
) {

    val remote = remoteDatasource
    val local = localDataSource

}