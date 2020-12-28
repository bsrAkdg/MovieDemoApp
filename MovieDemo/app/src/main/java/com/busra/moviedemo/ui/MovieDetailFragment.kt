package com.busra.moviedemo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.busra.moviedemo.R
import com.busra.moviedemo.databinding.FragmentMovieDetailBinding
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding : FragmentMovieDetailBinding
    private val viewModel: MovieViewModel by activityViewModels()
    private val args : MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        subscribeObserver()
        viewModel.getAllMovieDetail(args.movieId)
    }

    private fun subscribeObserver() {
        viewModel.movie.observe(viewLifecycleOwner, {
            binding.movie = it
        })
    }
}