package com.busra.moviedemo.ui.popularmovies

import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.repository.MovieRepository
import javax.inject.Inject

class InsertMovieUseCase
@Inject
constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun insertMovie(movie: Movie) = movieRepository.insertMovie(movie)
}