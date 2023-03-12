package com.dmribeiro.pokedex_app.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dmribeiro.pokedex_app.domain.model.Pokemon
import com.dmribeiro.pokedex_app.domain.repository.Repository
import com.dmribeiro.pokedex_app.presentation.state.Resource

//class PokemonPagingSource(
//    private val repository: Repository
//) : PagingSource<Int, Pokemon>() {
//
//    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
//        try {// Start refresh at page 1 if undefined.
//            val nextPageNumber = params.key ?: 1
//            val resource = repository.getAllPokemon(page = nextPageNumber)
//            Log.i("montalista", "dentro do paging - " + resource)
//
//
//
//            return if (resource is Resource.Success) {
//
//
//                if ((resource.data ?: 0) >= (resource.data.lastPage ?: 0)) {
//                    LoadResult.Page(
//                        data = resource.data,
//                        prevKey = null,
//                        nextKey = null
//                    )
//                } else if (resource.data.data.isEmpty()) {
//                    LoadResult.Page(
//                        data = resource.data.data,
//                        prevKey = null,
//                        nextKey = null
//                    )
//                } else {
//                    LoadResult.Page(
//                        data = resource.data.data,
//                        prevKey = null, // Apenas paginação para frente.
//                        nextKey = if ((resource.data.lastPage
//                                ?: 0) <= nextPageNumber
//                        ) null else (nextPageNumber + 1)
//                        //(resource.data.currentPage?:0) + 1
//                    )
//                }
//            } else if (resource is Resource.Fail) {
//                LoadResult.Error(Throwable(resource.status.name + " - " + resource.message))
//            } else {
//                LoadResult.Error(Throwable("Falha desconhecida"))
//            }
//        } catch (e: IOException) {
//            Log.i("montalista", "pediu carregamento IO - " + e)
//            // IOException for network failures.
//            return LoadResult.Error(e)
//        } catch (e: HttpException) {
//            Log.i("montalista", "pediu carregamento http - " + e)
//            // HttpException for any non-2xx HTTP status codes.
//            return LoadResult.Error(e)
//        } catch (e: Exception) {
//            Log.i("montalista", "pediu carregamento genérico - " + e)
//            // Handle errors in this block and return LoadResult.Error if it is an
//            // expected error (such as a network failure).
//            return LoadResult.Error(e)
//        }
//    }
//}
