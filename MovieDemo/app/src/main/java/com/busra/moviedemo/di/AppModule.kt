package com.busra.moviedemo.di

import android.content.Context
import androidx.room.Room
import com.busra.moviedemo.BuildConfig
import com.busra.moviedemo.data.remote.MovieApi
import com.busra.moviedemo.db.MovieDatabase
import com.busra.moviedemo.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(
        database: MovieDatabase
    ) = database.movieDao()

}