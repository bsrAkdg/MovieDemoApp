package com.busra.moviedemo.data.remote

import com.busra.moviedemo.SharedModel
import com.google.common.truth.Truth
import org.junit.Test

class MovieCallMapperTest {

    private val movieCallMapper = MovieCallMapper()

    @Test
    fun `map from entity success`() {
        val callMovie = SharedModel.callMovie
        val mappedMovie = movieCallMapper.mapFromEntity(callMovie)
        Truth.assertThat(mappedMovie).isEqualTo(SharedModel.cacheMovie)
    }

}