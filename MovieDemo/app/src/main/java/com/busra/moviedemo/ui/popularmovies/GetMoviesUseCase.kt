package com.busra.moviedemo.ui.popularmovies

import com.busra.moviedemo.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase
@Inject
constructor(
    private val movieRepository: MovieRepository
) {
    fun getAllMovies(page: Int) = movieRepository.getAllMovies(page)
}