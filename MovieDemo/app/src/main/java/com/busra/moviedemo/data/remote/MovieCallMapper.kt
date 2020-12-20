package com.busra.moviedemo.data.remote

import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.util.FromEntityMapper

class MovieCallMapper : FromEntityMapper<MovieCallEntity, Movie> {

    override fun mapFromEntity(entityModel: MovieCallEntity): Movie = Movie(
        id = entityModel.id,
        title = entityModel.title,
        overview = entityModel.overview,
        poster_path = entityModel.poster_path,
        vote_average = entityModel.vote_average
    )
}