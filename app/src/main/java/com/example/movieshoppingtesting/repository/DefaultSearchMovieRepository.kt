package com.example.movieshoppingtesting.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movieshoppingtesting.api.SearchMovieListApi
import com.example.movieshoppingtesting.models.entity.SearchMovieItem
import com.example.movieshoppingtesting.models.entity.SearchMovieListDao
import com.example.movieshoppingtesting.models.remote.SearchMovieImageResponse
import com.example.movieshoppingtesting.models.remote.SearchMovieListResponse
import com.example.movieshoppingtesting.util.Resource
import javax.inject.Inject

class DefaultSearchMovieRepository @Inject constructor(
        private val searchMovieListApi: SearchMovieListApi,
        private val searchMovieListDao: SearchMovieListDao
) : SearchMovieRepository{

        override suspend fun insertSearchMovieItem(searchMovieItem: SearchMovieItem) {
                searchMovieListDao.insertSearchMovieItem(searchMovieItem)
        }

        override suspend fun deleteSearchMovieItem(searchMovieItem: SearchMovieItem) {
                searchMovieListDao.deleteSearchMovieItem(searchMovieItem)
        }

        override fun getAllSearchMovieItem(): LiveData<List<SearchMovieItem>> {
                return searchMovieListDao.getAllSearchMovieItem()
        }

        override fun getTotalPrice(): LiveData<Float> {
                return searchMovieListDao.getTotalPrice()
        }

        override suspend fun getSearchMovieResultFromApi(
                pageQuery: Int,
                searchQuery: String
        ): Resource<SearchMovieListResponse> {
                return try {
                    val response = searchMovieListApi.getSearchMovieList(pageQuery,searchQuery)
                        if (response.isSuccessful) {
                                response.body()?.let { it ->
                                        return@let Resource.success(it)
                                } ?: Resource.error("Server Return Null", null)
                        } else {
                                Resource.error("An unknown error occurred", null)
                        }
                } catch (e: Exception) {
                        Log.d("EXCEPTION", "EXCEPTION: ", e)
                        Resource.error("Check your internet connection", null)
                }
        }


}