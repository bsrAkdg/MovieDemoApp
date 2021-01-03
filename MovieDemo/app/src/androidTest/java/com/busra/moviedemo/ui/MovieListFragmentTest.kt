package com.busra.moviedemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.busra.moviedemo.R
import com.busra.moviedemo.launchFragmentInHiltContainer
import com.busra.moviedemo.repository.FakeMovieRepositoryImpTest
import com.busra.moviedemo.ui.popularmovies.GetMovieDetailUseCase
import com.busra.moviedemo.ui.popularmovies.GetMoviesUseCase
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import com.busra.moviedemo.util.CustomFragmentFactoryTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class MovieListFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: CustomFragmentFactoryTest

    lateinit var testViewModel: MovieViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        val getMoviesUseCase = GetMoviesUseCase(FakeMovieRepositoryImpTest())
        val getMovieDetailUseCase = GetMovieDetailUseCase(FakeMovieRepositoryImpTest())
        testViewModel = MovieViewModel(getMoviesUseCase, getMovieDetailUseCase)
    }

    @Test
    fun recyclerViewShowMovieListIsSuccess() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.movie_graph)

        launchFragmentInHiltContainer<MovieListFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            this.viewModel = testViewModel
        }

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(
                0,
                click()
            )
        )

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.movieDetailFragment)
    }

}