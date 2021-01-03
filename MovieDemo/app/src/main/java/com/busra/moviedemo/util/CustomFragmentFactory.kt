package com.busra.moviedemo.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.busra.moviedemo.ui.MovieAdapter
import com.busra.moviedemo.ui.MovieDetailFragment
import com.busra.moviedemo.ui.MovieListFragment
import javax.inject.Inject

class CustomFragmentFactory
@Inject constructor(
    private val movieAdapter: MovieAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MovieListFragment::class.java.name -> MovieListFragment(movieAdapter = movieAdapter)
            MovieDetailFragment::class.java.name -> MovieDetailFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}