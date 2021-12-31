package com.example.movieshoppingtesting.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieshoppingtesting.R
import com.example.movieshoppingtesting.api.SearchMovieListApi
import com.example.movieshoppingtesting.database.MovieShoppingDatabase
import com.example.movieshoppingtesting.models.entity.SearchMovieListDao
import com.example.movieshoppingtesting.repository.DefaultSearchMovieRepository
import com.example.movieshoppingtesting.repository.SearchMovieRepository
import com.example.movieshoppingtesting.util.Constants.Companion.baseUrl
import com.example.movieshoppingtesting.util.Constants.Companion.databaseName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideSearchMovieListApi(retrofit: Retrofit) : SearchMovieListApi =
        retrofit.create(SearchMovieListApi::class.java)



    @Singleton
    @Provides
    fun provideMovieShoppingDatabase(app: Application) : MovieShoppingDatabase =
        Room.databaseBuilder(app, MovieShoppingDatabase::class.java, databaseName).build()

    @Singleton
    @Provides
    fun provideSearchMovieListDao(
        database: MovieShoppingDatabase
    ) : SearchMovieListDao = database.searchMovieListDao()


    @Singleton
    @Provides
    fun provideDefaultSearchMovieRepository(
        dao : SearchMovieListDao,
        api : SearchMovieListApi
    ) = DefaultSearchMovieRepository(api, dao) as SearchMovieRepository

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

}






