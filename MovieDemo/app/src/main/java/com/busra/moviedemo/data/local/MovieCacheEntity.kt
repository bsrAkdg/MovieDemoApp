package com.busra.moviedemo.data.local

data class MovieCacheEntity(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double?
)