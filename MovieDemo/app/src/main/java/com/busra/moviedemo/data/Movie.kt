package com.busra.moviedemo.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String? = null,
    val vote_average: Double? = null
)