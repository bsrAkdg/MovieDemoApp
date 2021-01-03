package com.busra.moviedemo.repository

import com.busra.moviedemo.SharedModel
import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.util.Constant
import com.busra.moviedemo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeMovieRepositoryImpTest constructor(private var isErrorCase: Boolean = false) : MovieRepository {

    override suspend fun insertMovies(movies: List<Movie>) {
    }

    override fun getAllMovies(page: Int): Flow<Resource<List<Movie>?>> = flow {
        if (isErrorCase) {
            emit(Resource.loading())
            emit(Resource.error<List<Movie>>(Constant.MOVIE_DETAIL_NOT_FOUND))
        } else {
            emit(Resource.loading())
            emit(Resource.success(listOf(SharedModel.movie, SharedModel.movie2)))
        }
    }

    override fun getAllMoviesDb(page: Int): Flow<Resource<List<Movie>?>> {
        TODO("Not yet implemented")
    }

    override fun getMovieDetail(id: Int): Flow<Resource<Movie?>> = flow {
        if (isErrorCase) {
            emit(Resource.loading())
            emit(Resource.error<Movie>(Constant.MOVIE_DETAIL_NOT_FOUND))
        } else {
            emit(Resource.loading())
            emit(Resource.success(SharedModel.movie))
        }
    }

    override fun getMovieDetailDb(id: Int): Flow<Resource<Movie?>> {
        TODO("Not yet implemented")
    }

    fun setErrorCase(isErrorCase: Boolean) {
        this.isErrorCase = isErrorCase
    }
}