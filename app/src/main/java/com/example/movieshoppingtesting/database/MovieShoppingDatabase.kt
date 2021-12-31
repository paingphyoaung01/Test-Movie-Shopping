package com.example.movieshoppingtesting.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieshoppingtesting.models.entity.SearchMovieItem
import com.example.movieshoppingtesting.models.entity.SearchMovieListDao

@Database(
    entities = [SearchMovieItem::class],
    version = 1
)
abstract class MovieShoppingDatabase : RoomDatabase(){

    abstract fun searchMovieListDao() : SearchMovieListDao
}