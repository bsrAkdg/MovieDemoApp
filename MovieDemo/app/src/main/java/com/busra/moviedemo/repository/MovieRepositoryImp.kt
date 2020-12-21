package com.busra.moviedemo.repository

import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.data.local.MovieCacheMapper
import com.busra.moviedemo.data.remote.MovieCallMapper
import com.busra.moviedemo.repository.datasource.LocalDataSource
import com.busra.moviedemo.repository.datasource.RemoteDataSource
import com.busra.moviedemo.util.Constant
import com.busra.moviedemo.util.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieRepositoryImp
@Inject
constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val cacheMapper: MovieCacheMapper,
    private val callMapper: MovieCallMapper
) : MovieRepository {

    override suspend fun insertMovie(movie: Movie) {
        localDataSource.insertMovie(cacheMapper.mapToEntity(movie))
    }

    override fun getAllMovies(page: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.loading(null))
        val response = remoteDataSource.getAllMovies(page = page)
        if (response.isSuccessful) {
            response.body()?.let { popularMovieResponse ->
                if (!popularMovieResponse.results.isNullOrEmpty()) {
                    val movies = popularMovieResponse.results.map { callMovie ->
                        callMapper.mapFromEntity(callMovie)
                    }
                    emit(Resource.success(movies))
                } else {
                    getAllMoviesDb(page = page)
                }
            } ?: getAllMoviesDb(page = page)
        } else {
            getAllMoviesDb(page = page)
        }
    }

    override fun getAllMoviesDb(page: Int) = flow {
        localDataSource.getAllMovies(page).collect { cacheMovies ->
            val movies = cacheMovies.map { cacheMovie ->
                cacheMapper.mapFromEntity(cacheMovie)
            }
            if (movies.isNullOrEmpty()) {
                emit(Resource.error(Constant.MOVIES_NOT_FOUND, null))
            } else {
                emit(Resource.success(movies))
            }
        }
    }

    override fun getMovieDetail(id: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.loading(null))
        val response = remoteDataSource.getMovieDetail(id)
        if (response.isSuccessful) {
            response.body()?.let { callMovie ->
                emit(Resource.success(callMapper.mapFromEntity(callMovie)))
            } ?: getMovieDetailDb(id)
        } else {
            getMovieDetailDb(id)
        }
    }

    override fun getMovieDetailDb(id: Int): Flow<Resource<Movie>> = flow {
        localDataSource.getMovieDetail(id).collect { cacheMovie ->
            val movie = cacheMapper.mapFromEntity(cacheMovie)
            emit(Resource.success(movie))
        }
    }

}