package com.dmribeiro.pokedex_app.data.local.model

data class Pagination<T>(
    val data: T,
    val total: Int?,
    val perPage: Int?,
    val currentPage: Int?,
    val lastPage: Int?,
    val nextPageUrl: String?,
    val prevPageUrl: String?,
    val from: Int?,
    val to: Int?
)
