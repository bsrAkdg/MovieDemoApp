package com.busra.moviedemo.ui.popularmovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.util.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieViewModel
@ViewModelInject
constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : BaseViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    // FIXME: 12/28/20 pagination
    fun getAllMovies() {
        viewModelScope.launch {
            getMoviesUseCase.getAllMovies(1).collect { resourceListMovie ->
                when (resourceListMovie.status) {
                    is Status.SUCCESS -> {
                        _movies.value = resourceListMovie.data
                    }
                    else -> {
                        handleErrorLoadingResource(resourceListMovie)
                    }
                }
            }
        }
    }

    fun getAllMovieDetail(id: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase.getAllMovieDetail(id).collect { resourceMovie ->
                when (resourceMovie.status) {
                    is Status.SUCCESS -> {
                        _movie.value = resourceMovie.data
                    }
                    else -> {
                        handleErrorLoadingResource(resourceMovie)
                    }
                }
            }
        }
    }
}