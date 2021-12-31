package com.example.movieshoppingtesting.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SEARCH_MOVIE_ITEMS")
data class SearchMovieItem(
    val title: String,
    val poster_path: String,
    val price : Float,
    val amount: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
//    val adult: Boolean,
//    val backdrop_path: String,
//    val genre_ids: List<Int>,
//    val original_language: String,
//    val original_title: String,
//    val overview: String,
//    val popularity: Double,
//    val release_date: String,
//    val video: Boolean,
//    val vote_average: Double,
//    val vote_count: Int
)