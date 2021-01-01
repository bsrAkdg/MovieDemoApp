package com.busra.moviedemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.busra.moviedemo.R
import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.databinding.FragmentMovieListBinding
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import com.busra.moviedemo.util.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Movies.
 */
@AndroidEntryPoint
class MovieListFragment constructor(
    val movieAdapter : MovieAdapter
) : Fragment(R.layout.fragment_movie_list), OnItemClickListener<Movie> {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieListBinding.bind(view).apply {
            movieAdapter.setOnItemClick(this@MovieListFragment)
            recyclerView.apply {
                adapter = movieAdapter
                addItemDecoration(
                    SpaceItemDecoration(
                        resources.getDimension(R.dimen.space_16).toInt()
                    )
                )
            }
        }

        subscribeObserver()
        viewModel.getAllMovies()
    }

    private fun subscribeObserver() {
        viewModel.movies.observe(viewLifecycleOwner, { movieList ->
            movieAdapter.submitList(movieList)
        })
    }

    override fun onItemClick(item: Movie) {
        val bundle = bundleOf("movieId" to item.id)
        findNavController().navigate(
            R.id.action_movieListFragment_to_movieDetailFragment,
            bundle
        )
    }
}