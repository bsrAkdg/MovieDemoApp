package com.busra.moviedemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.busra.moviedemo.data.local.MovieCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieCacheEntity)

    @Query("SELECT * FROM movie_db")
    fun getAllMovies() : Flow<List<MovieCacheEntity>>

    @Query("SELECT * FROM movie_db WHERE id = :id")
    fun getMovieDetail(id: Int) : Flow<MovieCacheEntity>
}