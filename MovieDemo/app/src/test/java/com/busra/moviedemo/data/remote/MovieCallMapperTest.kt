package com.busra.moviedemo.data.remote

import com.busra.moviedemo.SharedModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MovieCallMapperTest {

    private val movieCallMapper = MovieCallMapper()

    @Test
    fun `map from entity success`() {
        val callMovie = SharedModel.callMovie
        val mappedMovie = movieCallMapper.mapFromEntity(callMovie)
        assertThat(mappedMovie).isEqualTo(SharedModel.movie)
    }

}