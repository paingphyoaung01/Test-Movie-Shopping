package com.example.movieshoppingtesting.api

import com.example.movieshoppingtesting.models.remote.SearchMovieImageResponse
import com.example.movieshoppingtesting.models.remote.SearchMovieListResponse
import com.example.movieshoppingtesting.util.Constants.Companion.apiKey
import com.example.movieshoppingtesting.util.Constants.Companion.language
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchMovieListApi {

    @GET("search/movie")
    suspend fun getSearchMovieList(
        @Query("page")
        Page: Int,
        @Query("query")
        Query: String?,
        @Query("api_key")
        API_KEY: String = apiKey,
        @Query("include_adult")
        Include_Adult: Boolean = false,
        @Query("language")
        Language: String = language
    ): Response<SearchMovieListResponse>

}