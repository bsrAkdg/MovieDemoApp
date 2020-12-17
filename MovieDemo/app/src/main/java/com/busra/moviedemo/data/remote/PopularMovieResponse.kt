package com.busra.moviedemo.data.remote

data class PopularMovieResponse(
    val page: Int,
    val results: List<MovieCallEntity>,
    val total_pages: Int,
    val total_results: Int
)