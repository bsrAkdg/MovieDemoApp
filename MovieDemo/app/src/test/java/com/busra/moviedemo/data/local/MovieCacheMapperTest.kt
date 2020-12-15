package com.busra.moviedemo.data.local

import com.busra.moviedemo.SharedModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MovieCacheMapperTest {

    private val movieCacheMapper = MovieCacheMapper()

    @Test
    fun `map from entity success`() {
        val cacheMovie = SharedModel.cacheMovie
        val mappedMovie = movieCacheMapper.mapFromEntity(cacheMovie)
        assertThat(mappedMovie).isEqualTo(SharedModel.movie)
    }

    @Test
    fun `map to entity success`() {
        val movie = SharedModel.movie
        val mappedMovie = movieCacheMapper.mapToEntity(movie)
        assertThat(mappedMovie).isEqualTo(SharedModel.cacheMovie)
    }
}