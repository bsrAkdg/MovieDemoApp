package com.busra.moviedemo.di

import android.content.Context
import androidx.room.Room
import com.busra.moviedemo.BuildConfig
import com.busra.moviedemo.data.remote.MovieApi
import com.busra.moviedemo.db.MovieDatabase
import com.busra.moviedemo.util.Constant
import com.busra.moviedemo.util.Constant.DATABASE_NAME
import com.busra.moviedemo.util.NoConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) : Context =  context

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

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor,
                            networkInterceptor : NoConnectionInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(logger)
        }
        return okHttpClient
            .addInterceptor(networkInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()

        val url = original.url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", Constant.LANGUAGE)
            .build()

        val req = original
            .newBuilder()
            .url(url)
            .build()

        chain.proceed(req)
    }

    @Singleton
    @Provides
    fun provideMovieApi(
        okHttpClient: OkHttpClient
    ): MovieApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(MovieApi::class.java)

}