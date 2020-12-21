package com.busra.moviedemo.repository

import com.busra.moviedemo.data.remote.MovieApi
import com.busra.moviedemo.data.remote.MovieCallEntity
import com.busra.moviedemo.data.remote.PopularMovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource
@Inject
constructor(
    private val movieApi: MovieApi
) {
    suspend fun getAllMovies(page: Int): Response<PopularMovieResponse> = movieApi.getPopularMovie(page = page)

    suspend fun getMovieDetail(id: Int): Response<MovieCallEntity> = movieApi.getMovieDetail(movieId = id)
}