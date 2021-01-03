package com.busra.moviedemo.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.busra.moviedemo.repository.FakeMovieRepositoryImpTest
import com.busra.moviedemo.ui.MovieAdapter
import com.busra.moviedemo.ui.MovieDetailFragment
import com.busra.moviedemo.ui.MovieListFragment
import com.busra.moviedemo.ui.popularmovies.GetMovieDetailUseCase
import com.busra.moviedemo.ui.popularmovies.GetMoviesUseCase
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import javax.inject.Inject

class CustomFragmentFactoryTest
@Inject constructor(
    val movieAdapter: MovieAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MovieListFragment::class.java.name -> MovieListFragment(
                movieAdapter = movieAdapter,
                viewModel = MovieViewModel(GetMoviesUseCase(FakeMovieRepositoryImpTest()),
                    GetMovieDetailUseCase(FakeMovieRepositoryImpTest())
                )
            )
            MovieDetailFragment::class.java.name -> MovieDetailFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}