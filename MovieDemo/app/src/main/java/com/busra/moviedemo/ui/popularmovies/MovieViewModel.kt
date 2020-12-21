package com.busra.moviedemo.ui.popularmovies

import androidx.hilt.lifecycle.ViewModelInject

class MovieViewModel
@ViewModelInject
constructor(
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : BaseViewModel() {


}