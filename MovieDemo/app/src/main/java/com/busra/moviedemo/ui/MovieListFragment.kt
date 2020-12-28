package com.busra.moviedemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.busra.moviedemo.R
import com.busra.moviedemo.databinding.FragmentMovieListBinding
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Movies.
 */
@AndroidEntryPoint
class MovieListFragment constructor(
    val movieAdapter : MovieAdapter
) : Fragment(R.layout.fragment_movie_list) {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view).apply {
            recyclerView.adapter = movieAdapter
        }
        subscribeObserver()
        viewModel.getAllMovies()
    }

    private fun subscribeObserver() {
        viewModel.movies.observe(viewLifecycleOwner, { movieList ->
            movieAdapter.submitList(movieList)
        })
    }
}