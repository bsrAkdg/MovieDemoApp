package com.busra.moviedemo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int
    ): Response<PopularMovieResponse>

    @GET("/movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int
    ): Response<MovieCallEntity>
}