package com.busra.moviedemo.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.databinding.MovieListItemBinding
import com.busra.moviedemo.ui.MovieAdapter.MovieViewHolder
import javax.inject.Inject

class MovieAdapter
@Inject
constructor(
): ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {

    private lateinit var listener : OnItemClickListener<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position), listener)
    }

    fun setOnItemClick(listener : OnItemClickListener<Movie>) {
        this.listener = listener
    }

    class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movie: Movie, listener : OnItemClickListener<Movie>) {
            with(binding) {
                this.movie = movie
                this.listener = listener
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding)
            }
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem

}

