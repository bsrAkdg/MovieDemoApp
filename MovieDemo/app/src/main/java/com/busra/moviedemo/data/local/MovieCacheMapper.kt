package com.busra.moviedemo.data.local

import com.busra.moviedemo.data.Movie
import com.busra.moviedemo.util.EntityMapper

class MovieCacheMapper : EntityMapper<MovieCacheEntity, Movie> {

    override fun mapFromEntity(entityModel: MovieCacheEntity): Movie = Movie(
        id = entityModel.id,
        title = entityModel.title,
        overview = entityModel.overview,
        poster_path = entityModel.poster_path,
        vote_average = entityModel.vote_average
    )

    override fun mapToEntity(domainModel: Movie): MovieCacheEntity = MovieCacheEntity(
        id = domainModel.id,
        title = domainModel.title,
        overview = domainModel.overview,
        poster_path = domainModel.poster_path,
        vote_average = domainModel.vote_average
    )
}