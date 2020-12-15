package com.busra.moviedemo.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.busra.moviedemo.SharedModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var movieDatabase: MovieDatabase
    private lateinit var movieDao: MovieDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        movieDao = movieDatabase.movieDao()
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
        movieDatabase.close()
    }

}