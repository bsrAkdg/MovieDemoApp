package com.busra.moviedemo.ui.popularmovies

import com.busra.moviedemo.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase
@Inject
constructor(
    private val movieRepository: MovieRepository
) {
    fun getAllMovieDetail(id: Int) = movieRepository.getMovieDetail(id)
}