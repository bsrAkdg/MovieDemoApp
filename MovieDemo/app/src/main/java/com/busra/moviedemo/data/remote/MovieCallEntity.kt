package com.busra.moviedemo.data.remote

data class MovieCallEntity(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    val id: Int,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
) {

    override fun equals(other: Any?): Boolean {
        return id == (other as MovieCallEntity).id
                && title == other.title
                && overview == other.overview
    }
}