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

    override suspend fun insertMovies(movies: List<Movie>) {
        movies.map { movie ->
            localDataSource.insertMovie(cacheMapper.mapToEntity(movie))
        }
    }

    override fun getAllMovies(page: Int): Flow<Resource<List<Movie>?>> = flow {
        emit(Resource.loading())
        val response = remoteDataSource.getAllMovies(page = page)
        if (response.isSuccessful) {
            response.body()?.let { popularMovieResponse ->
                popularMovieResponse.results?.let { results ->
                    val movies = results.map { callMovie ->
                        callMapper.mapFromEntity(callMovie)
                    }
                    insertMovies(movies)
                    emit(Resource.success(movies))
                } ?: getAllMoviesDb(page = page).collect {
                    emit(it)
                }
            } ?: getAllMoviesDb(page = page).collect {
                emit(it)
            }
        } else {
            getAllMoviesDb(page = page).collect {
                emit(it)
            }
        }
    }

    override fun getAllMoviesDb(page: Int) = flow {
        localDataSource.getAllMovies(page).collect { cacheMovies ->
            if(!cacheMovies.isNullOrEmpty()) {
                val movies = cacheMovies.map { cacheMovie ->
                    cacheMapper.mapFromEntity(cacheMovie)
                }
                emit(Resource.success(movies))
            } else {
                emit(Resource.error(Constant.MOVIES_NOT_FOUND, null))
            }
        }
    }

    override fun getMovieDetail(id: Int): Flow<Resource<Movie?>> = flow {
        emit(Resource.loading())
        val response = remoteDataSource.getMovieDetail(id)
        if (response.isSuccessful) {
            response.body()?.let { callMovie ->
                emit(Resource.success(callMapper.mapFromEntity(callMovie)))
            } ?: getMovieDetailDb(id).collect {
                emit(it)
            }
        } else {
            getMovieDetailDb(id).collect {
                emit(it)
            }
        }
    }

    override fun getMovieDetailDb(id: Int): Flow<Resource<Movie?>> = flow {
        localDataSource.getMovieDetail(id).collect { cacheMovie ->
            cacheMovie?.let {
                val movie = cacheMapper.mapFromEntity(cacheMovie)
                emit(Resource.success(movie))
            } ?: emit(Resource.error(Constant.MOVIE_DETAIL_NOT_FOUND, null))
        }
    }

}