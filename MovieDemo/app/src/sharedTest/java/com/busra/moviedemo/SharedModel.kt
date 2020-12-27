package com.busra.moviedemo

import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.data.local.MovieCacheEntity
import com.busra.moviedemo.data.remote.MovieCallEntity

object SharedModel {

    val callMovie = MovieCallEntity(
        id = 1,
        title = "First Movie",
        overview = "First movie overview",
        poster_path = "path1",
        vote_average = 4.0
    )

    val movie = Movie(
        1,
        "First Movie",
        "First movie overview",
        "path1",
        4.0
    )

    val cacheMovie = MovieCacheEntity(
        1,
        "First Movie",
        "First movie overview",
        "path1",
        4.0
    )

    val callMovie2 = MovieCallEntity(
        id=2,
        title = "Second Movie",
        overview = "Second movie overview",
        poster_path = "path2",
        vote_average = 3.0
    )

    val movie2 = Movie(
        2,
        "Second Movie",
        "Second movie overview",
        "path2",
        3.0
    )

    val cacheMovie2 = MovieCacheEntity(
        2,
        "Second Movie",
        "Second movie overview",
        "path2",
        3.0
    )

}