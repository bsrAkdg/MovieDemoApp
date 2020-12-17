package com.busra.moviedemo.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.busra.moviedemo.SharedModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("movie_db")
    lateinit var database: MovieDatabase

    private lateinit var movieDao : MovieDao

    @Before
    fun setup() {
        hiltRule.inject()
        movieDao = database.movieDao()
    }

    @Test
    fun insertMovieSuccess() = runBlockingTest {
        movieDao.insertMovie(SharedModel.cacheMovie)
        val movies = movieDao.getAllMovies().first()
        assertThat(movies).contains(SharedModel.cacheMovie)
    }

    @Test
    fun getAllMoviesSuccess() = runBlockingTest {
        movieDao.insertMovie(SharedModel.cacheMovie)
        movieDao.insertMovie(SharedModel.cacheMovie2)
        val movies = movieDao.getAllMovies().first()
        assertThat(movies).hasSize(2)
    }

    @Test
    fun getMovieDetailSuccess() = runBlockingTest {
        movieDao.insertMovie(SharedModel.cacheMovie)
        val movie = movieDao.getMovieDetail(SharedModel.cacheMovie.id).first()
        assertThat(movie).isEqualTo(SharedModel.cacheMovie)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

}