package com.busra.moviedemo.repository

import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun insertMovies(movies: List<Movie>)
    fun getAllMovies(page: Int) : Flow<Resource<List<Movie>>>
    fun getAllMoviesDb(page: Int) : Flow<Resource<List<Movie>?>>
    fun getMovieDetail(id: Int) : Flow<Resource<Movie>>
    fun getMovieDetailDb(id: Int) : Flow<Resource<Movie>>

}