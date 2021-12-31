package com.example.movieshoppingtesting.repository

import androidx.lifecycle.LiveData
import com.example.movieshoppingtesting.models.entity.SearchMovieItem
import com.example.movieshoppingtesting.models.remote.SearchMovieImageResponse
import com.example.movieshoppingtesting.models.remote.SearchMovieListResponse
import com.example.movieshoppingtesting.util.Resource

interface SearchMovieRepository {

    suspend fun insertSearchMovieItem(searchMovieItem: SearchMovieItem)

    suspend fun deleteSearchMovieItem(searchMovieItem: SearchMovieItem)

    fun getAllSearchMovieItem() : LiveData<List<SearchMovieItem>>

    fun getTotalPrice() : LiveData<Float>

    suspend fun getSearchMovieResultFromApi(pageQuery: Int, searchQuery: String) : Resource<SearchMovieListResponse>
}