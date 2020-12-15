package com.busra.moviedemo.data.local

data class MovieCacheEntity(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double?
) {
    override fun equals(other: Any?): Boolean {
        return id == (other as MovieCacheEntity).id
                && title == other.title
                && overview == other.overview
    }
}