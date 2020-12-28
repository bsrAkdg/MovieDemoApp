package com.busra.moviedemo.data.remote

import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.util.FromEntityMapper
import javax.inject.Inject

class MovieCallMapper
@Inject constructor(
) : FromEntityMapper<MovieCallEntity, Movie> {

    override fun mapFromEntity(entityModel: MovieCallEntity): Movie = Movie(
        id = entityModel.id,
        title = entityModel.title,
        overview = entityModel.overview,
        poster_path = "https://image.tmdb.org/t/p/w500/${entityModel.poster_path}",
        vote_average = entityModel.vote_average
    )
}