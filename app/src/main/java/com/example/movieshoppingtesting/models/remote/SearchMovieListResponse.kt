package com.example.movieshoppingtesting.models.remote

data class SearchMovieListResponse(
    val page: Int,
    val results: List<SearchMovieImageResponse>,
    val total_pages: Int,
    val total_results: Int
)

data class SearchMovieImageResponse(
    val id: Int,
    val poster_path: String,
    val title: String,
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