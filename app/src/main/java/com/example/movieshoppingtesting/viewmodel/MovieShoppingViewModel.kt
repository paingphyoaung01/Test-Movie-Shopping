package com.example.movieshoppingtesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieshoppingtesting.models.entity.SearchMovieItem
import com.example.movieshoppingtesting.models.remote.SearchMovieImageResponse
import com.example.movieshoppingtesting.models.remote.SearchMovieListResponse
import com.example.movieshoppingtesting.repository.SearchMovieRepository
import com.example.movieshoppingtesting.util.Constants.Companion.MAX_NAME_LENGTH
import com.example.movieshoppingtesting.util.Constants.Companion.MAX_PRICE_LENGTH
import com.example.movieshoppingtesting.util.Event
import com.example.movieshoppingtesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieShoppingViewModel @Inject constructor(
    private val repository: SearchMovieRepository
) : ViewModel() {

    val movieItems = repository.getAllSearchMovieItem()

    val totalPrice = repository.getTotalPrice()

    private val _images = MutableLiveData<Event<Resource<SearchMovieListResponse>>>()
    val images : LiveData<Event<Resource<SearchMovieListResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl : LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<SearchMovieItem>>>()
    val insertShoppingItemStatus : LiveData<Event<Resource<SearchMovieItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url : String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(searchMovieItem: SearchMovieItem) = viewModelScope.launch {
        repository.deleteSearchMovieItem(searchMovieItem)
    }

    fun insertSearchMovieItemIntoDb(searchMovieItem: SearchMovieItem) = viewModelScope.launch {
        repository.insertSearchMovieItem(searchMovieItem)
    }

    fun checkUserInsertData(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }
        if (name.length > MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Name count is must not be over $MAX_NAME_LENGTH", null)))
            return
        }
        if (priceString.length > MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Price count is must not be over $MAX_PRICE_LENGTH", null)))
            return
        }
        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))
            return
        }
        val searchMovieItem = SearchMovieItem(name, _curImageUrl.value ?: "", priceString.toFloat(), amount )
        insertSearchMovieItemIntoDb(searchMovieItem)
        setCurImageUrl("")
        _insertShoppingItemStatus.value = Event(Resource.success(searchMovieItem))
    }

    fun searchForImage(pageQuery: String ,imageQuery : String) {
        if (imageQuery.isEmpty() || pageQuery.isEmpty()) {
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getSearchMovieResultFromApi(pageQuery.toInt(), imageQuery)
            _images.value = Event(response)
        }
    }
}