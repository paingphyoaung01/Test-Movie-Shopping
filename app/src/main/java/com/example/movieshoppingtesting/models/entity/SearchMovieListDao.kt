package com.example.movieshoppingtesting.models.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SearchMovieListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovieItem(searchMovieItem: SearchMovieItem)

    @Delete
    suspend fun deleteSearchMovieItem(searchMovieItem: SearchMovieItem)

    @Query("SELECT * FROM SEARCH_MOVIE_ITEMS")
    fun getAllSearchMovieItem() : LiveData<List<SearchMovieItem>>

    @Query("SELECT SUM(price * amount) FROM SEARCH_MOVIE_ITEMS")
    fun getTotalPrice() : LiveData<Float>
}