package com.busra.moviedemo.di

import android.content.Context
import androidx.room.Room
import com.busra.moviedemo.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object TestAppModule {

    @Provides
    @Named("movie_db")
    fun provideMovieDatabase(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(
        context,
        MovieDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()
}