package com.example.movieshoppingtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieshoppingtesting.models.entity.SearchMovieItem
import com.example.movieshoppingtesting.models.remote.SearchMovieImageResponse
import com.example.movieshoppingtesting.util.Resource

class FakeSearchMovieRepository : SearchMovieRepository{

    private val searchMovieItems = mutableListOf<SearchMovieItem>()

    private val getAllSearchMovieItem = MutableLiveData<List<SearchMovieItem>>()

    private val getTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value : Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        getAllSearchMovieItem.postValue(searchMovieItems)
        getTotalPrice.postValue(getPriceTotal())
    }

    private fun getPriceTotal() : Float {
        return searchMovieItems.sumOf {
            it.price.toDouble()
        }.toFloat()
    }

    override suspend fun insertSearchMovieItem(searchMovieItem: SearchMovieItem) {
        searchMovieItems.add(searchMovieItem)
        refreshLiveData()
    }

    override suspend fun deleteSearchMovieItem(searchMovieItem: SearchMovieItem) {
        searchMovieItems.remove(searchMovieItem)
        refreshLiveData()
    }

    override fun getAllSearchMovieItem(): LiveData<List<SearchMovieItem>> {
        return getAllSearchMovieItem
    }

    override fun getTotalPrice(): LiveData<Float> {
        return getTotalPrice
    }

    override suspend fun getSearchMovieResultFromApi(
        pageQuery: Int,
        searchQuery: String
    ): Resource<SearchMovieImageResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("Error", null)
        } else {
            Resource.success(SearchMovieImageResponse(1,"poster_path", "title"))
        }
    }


}