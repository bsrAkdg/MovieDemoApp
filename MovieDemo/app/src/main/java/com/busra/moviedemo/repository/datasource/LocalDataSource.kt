package com.busra.moviedemo.repository.datasource

import com.busra.moviedemo.data.local.MovieCacheEntity
import com.busra.moviedemo.db.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource
@Inject
constructor(
    private val movieDao: MovieDao
) {
    suspend fun insertMovie(movieCacheEntity: MovieCacheEntity) = movieDao.insertMovie(movie = movieCacheEntity)

    fun getAllMovies(page: Int): Flow<List<MovieCacheEntity>> = movieDao.getAllMovies()

    fun getMovieDetail(id: Int): Flow<MovieCacheEntity> = movieDao.getMovieDetail(id)
}