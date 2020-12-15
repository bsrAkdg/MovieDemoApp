package com.busra.moviedemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.busra.moviedemo.data.local.MovieCacheEntity

@Database(
    entities = [MovieCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}