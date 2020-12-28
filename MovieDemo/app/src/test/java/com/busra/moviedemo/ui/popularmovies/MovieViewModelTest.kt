package com.busra.moviedemo.ui.popularmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bsrakdg.shoppingapp.MainCoroutineRule
import com.bsrakdg.shoppingapp.getOrAwaitValueTest
import com.busra.moviedemo.SharedModel
import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Mock
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun setup() {
        movieViewModel = MovieViewModel(getMoviesUseCase, getMovieDetailUseCase)
    }

    @Test
    fun `get all movies show loading`() = runBlockingTest {
        val flow = flow {
            emit(Resource.loading(null))
        }

        `when`(getMoviesUseCase.getAllMovies(1)).thenReturn(flow)

        movieViewModel.getAllMovies()

        val result = movieViewModel.mainStateView.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.isLoading).isTrue()
    }

    @Test
    fun `get all movies show error`() = runBlockingTest {
        val flow = flow {
            val data : List<Movie> = emptyList()
            emit(Resource.error("Not found", data))
        }

        `when`(getMoviesUseCase.getAllMovies(1)).thenReturn(flow)

        movieViewModel.getAllMovies()

        val result = movieViewModel.mainStateView.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.isError).isTrue()
    }

    @Test
    fun `get all movies show success`() = runBlockingTest {
        val data : List<Movie> = listOf(SharedModel.movie)

        val flow = flow {
            emit(Resource.success(data))
        }

        `when`(getMoviesUseCase.getAllMovies(1)).thenReturn(flow)

        movieViewModel.getAllMovies()

        val result = movieViewModel.movies.getOrAwaitValueTest()
        assertThat(result).isEqualTo(data)
    }

    @Test
    fun `get movie detail show loading`() = runBlockingTest {
        val flow = flow {
            emit(Resource.loading(null))
        }

        `when`(getMovieDetailUseCase.getAllMovieDetail(1)).thenReturn(flow)

        movieViewModel.getAllMovieDetail(1)

        val result = movieViewModel.mainStateView.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.isLoading).isTrue()
    }

    @Test
    fun `get movie detail show error`() = runBlockingTest {
        val flow = flow {
            emit(Resource.error("Not found", null))
        }

        `when`(getMovieDetailUseCase.getAllMovieDetail(1)).thenReturn(flow)

        movieViewModel.getAllMovieDetail(1)

        val result = movieViewModel.mainStateView.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.isError).isTrue()
    }

    @Test
    fun `get movie detail show success`() = runBlockingTest {
        val data = SharedModel.movie

        val flow = flow {
            emit(Resource.success(data))
        }

        `when`(getMovieDetailUseCase.getAllMovieDetail(1)).thenReturn(flow)

        movieViewModel.getAllMovieDetail(1)

        val result = movieViewModel.movie.getOrAwaitValueTest()
        assertThat(result).isEqualTo(data)
    }
}