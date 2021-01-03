package com.busra.moviedemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.busra.moviedemo.launchFragmentInHiltContainer
import com.busra.moviedemo.repository.FakeMovieRepositoryImpTest
import com.busra.moviedemo.ui.popularmovies.GetMovieDetailUseCase
import com.busra.moviedemo.ui.popularmovies.GetMoviesUseCase
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@MediumTest
class MovieDetailFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        val getMoviesUseCase = GetMoviesUseCase(FakeMovieRepositoryImpTest())
        val getMovieDetailUseCase = GetMovieDetailUseCase(FakeMovieRepositoryImpTest())
        viewModel = MovieViewModel(getMoviesUseCase, getMovieDetailUseCase)
    }
}