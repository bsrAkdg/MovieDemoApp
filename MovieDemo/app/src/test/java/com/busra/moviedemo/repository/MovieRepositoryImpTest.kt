package com.busra.moviedemo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bsrakdg.shoppingapp.MainCoroutineRule
import com.busra.moviedemo.SharedModel
import com.busra.moviedemo.data.local.MovieCacheMapper
import com.busra.moviedemo.data.remote.MovieCallEntity
import com.busra.moviedemo.data.remote.MovieCallMapper
import com.busra.moviedemo.data.remote.PopularMovieResponse
import com.busra.moviedemo.repository.datasource.LocalDataSource
import com.busra.moviedemo.repository.datasource.RemoteDataSource
import com.busra.moviedemo.util.Constant
import com.busra.moviedemo.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImpTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var localDataSource : LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var cacheMapper: MovieCacheMapper

    @Mock
    private lateinit var callMapper: MovieCallMapper


    private lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {
        movieRepository = MovieRepositoryImp(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            cacheMapper = cacheMapper,
            callMapper = callMapper
        )
    }

    @Test
    fun insertMovies() = runBlockingTest {
        val movies = listOf(SharedModel.movie, SharedModel.movie2)

        `when`(cacheMapper.mapToEntity(SharedModel.movie)).thenReturn(SharedModel.cacheMovie)
        `when`(cacheMapper.mapToEntity(SharedModel.movie2)).thenReturn(SharedModel.cacheMovie2)

        movieRepository.insertMovies(movies)

        Mockito.verify(localDataSource).insertMovie(SharedModel.cacheMovie)
        Mockito.verify(localDataSource).insertMovie(SharedModel.cacheMovie2)
    }

    @Test
    fun `getAllMovies movies success`() = runBlockingTest {

        val response = PopularMovieResponse(1, listOf(SharedModel.callMovie, SharedModel.callMovie2), 1, 2)
        `when`(remoteDataSource.getAllMovies(1)).thenReturn(Response.success(response))
        `when`(callMapper.mapFromEntity(SharedModel.callMovie)).thenReturn(SharedModel.movie)
        `when`(callMapper.mapFromEntity(SharedModel.callMovie2)).thenReturn(SharedModel.movie2)

        val result = movieRepository.getAllMovies(1).drop(1).first()
        assertThat(result.data).isEqualTo(listOf(SharedModel.movie, SharedModel.movie2))
    }

    @Test
    fun `getAllMovies movies success but empty body`() = runBlockingTest {

        `when`(remoteDataSource.getAllMovies(1)).thenReturn(Response.success<PopularMovieResponse>(200, null))

        val flow = flow {
            emit(listOf(SharedModel.cacheMovie, SharedModel.cacheMovie2))
        }

        `when`(localDataSource.getAllMovies(1)).thenReturn(flow)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie)).thenReturn(SharedModel.movie)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie2)).thenReturn(SharedModel.movie2)

        val result = movieRepository.getAllMovies(1).drop(1).first()
        assertThat(result.data).isEqualTo(listOf(SharedModel.movie, SharedModel.movie2))

    }

    @Test
    fun `getAllMovies movies error`() = runBlockingTest {

        `when`(remoteDataSource.getAllMovies(1)).thenReturn(Response.error(400, "".toResponseBody()))

        val flow = flow {
            emit(listOf(SharedModel.cacheMovie, SharedModel.cacheMovie2))
        }

        `when`(localDataSource.getAllMovies(1)).thenReturn(flow)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie)).thenReturn(SharedModel.movie)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie2)).thenReturn(SharedModel.movie2)

        val result = movieRepository.getAllMovies(1).drop(1).first()
        assertThat(result.data).isEqualTo(listOf(SharedModel.movie, SharedModel.movie2))
    }


    @Test
    fun `getAllMoviesDb movies success`() = runBlockingTest {
        val flow = flow {
            emit(listOf(SharedModel.cacheMovie, SharedModel.cacheMovie2))
        }

        `when`(localDataSource.getAllMovies(1)).thenReturn(flow)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie)).thenReturn(SharedModel.movie)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie2)).thenReturn(SharedModel.movie2)

        val result = movieRepository.getAllMoviesDb(1).first()

        assertThat(result.data).isEqualTo(listOf(SharedModel.movie, SharedModel.movie2))
    }

    @Test
    fun `getAllMoviesDb movies not found`() = runBlockingTest {
        val flow = flow {
            emit(null)
        }

        `when`(localDataSource.getAllMovies(1)).thenReturn(flow)

        val result = movieRepository.getAllMoviesDb(1).first()
        val expected = Resource.error(Constant.MOVIES_NOT_FOUND, null)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getMovieDetail movie detail success`() = runBlockingTest {

        `when`(remoteDataSource.getMovieDetail(1)).thenReturn(Response.success(SharedModel.callMovie))
        `when`(callMapper.mapFromEntity(SharedModel.callMovie)).thenReturn(SharedModel.movie)

        val result = movieRepository.getMovieDetail(1).drop(1).first()
        assertThat(result.data).isEqualTo(SharedModel.movie)
    }

    @Test
    fun `getMovieDetail movie detail success but empty body`() = runBlockingTest {

        `when`(remoteDataSource.getMovieDetail(1)).thenReturn(Response.success<MovieCallEntity>(200, null))

        val flow = flow {
            emit(SharedModel.cacheMovie)
        }

        `when`(localDataSource.getMovieDetail(1)).thenReturn(flow)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie)).thenReturn(SharedModel.movie)

        val result = movieRepository.getMovieDetail(1).drop(1).first()
        assertThat(result.data).isEqualTo(SharedModel.movie)

    }

    @Test
    fun `getMovieDetail movie detail error`() = runBlockingTest {

        `when`(remoteDataSource.getMovieDetail(1)).thenReturn(Response.error(400, "".toResponseBody()))

        val flow = flow {
            emit(SharedModel.cacheMovie)
        }

        `when`(localDataSource.getMovieDetail(1)).thenReturn(flow)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie)).thenReturn(SharedModel.movie)

        val result = movieRepository.getMovieDetail(1).drop(1).first()
        assertThat(result.data).isEqualTo(SharedModel.movie)
    }

    @Test
    fun `getMovieDetailDb movie detail success`() = runBlockingTest {
        val flow = flow {
            emit(SharedModel.cacheMovie)
        }

        `when`(localDataSource.getMovieDetail(1)).thenReturn(flow)
        `when`(cacheMapper.mapFromEntity(SharedModel.cacheMovie)).thenReturn(SharedModel.movie)

        val result = movieRepository.getMovieDetailDb(1).first()

        assertThat(result).isEqualTo(Resource.success(SharedModel.movie))
    }

    @Test
    fun `getMovieDetailDb movie detail not found`() = runBlockingTest {
        val flow = flow {
            emit(null)
        }

        `when`(localDataSource.getMovieDetail(1)).thenReturn(flow)

        val result = movieRepository.getMovieDetailDb(1).first()
        val expected = Resource.error(Constant.MOVIE_DETAIL_NOT_FOUND, null)
        assertThat(result).isEqualTo(expected)
    }
}