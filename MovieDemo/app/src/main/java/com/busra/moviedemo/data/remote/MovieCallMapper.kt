package com.busra.moviedemo.data.remote

import com.busra.moviedemo.data.local.MovieCacheEntity
import com.busra.moviedemo.util.EntityMapper

class MovieCallMapper : EntityMapper<MovieCallEntity, MovieCacheEntity> {

    override fun mapFromEntity(entityModel: MovieCallEntity): MovieCacheEntity = MovieCacheEntity(
        entityModel.id,
        entityModel.title,
        entityModel.overview,
        entityModel.poster_path,
        entityModel.vote_average
    )

    // FIXME: 12/15/20 : unnecessary implementation
    override fun mapToEntity(domainModel: MovieCacheEntity): MovieCallEntity = MovieCallEntity(
        id = domainModel.id,
        overview = domainModel.overview,
        poster_path = domainModel.poster_path,
        title = domainModel.title,
        vote_average = domainModel.vote_average
    )

}