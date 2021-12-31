package com.example.movieshoppingtesting.models.entity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.movieshoppingtesting.database.MovieShoppingDatabase
import com.example.movieshoppingtesting.getOrAwaitValue
import com.example.movieshoppingtesting.launchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class SearchMovieListDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var movieShoppingDatabase: MovieShoppingDatabase

    private lateinit var searchMovieListDao: SearchMovieListDao

    @Before
    fun setup() {
//        movieShoppingDatabase = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            MovieShoppingDatabase::class.java
//        ).allowMainThreadQueries().build()
        hiltRule.inject()
        searchMovieListDao = movieShoppingDatabase.searchMovieListDao()
    }

    @After
    fun teardown() {
        movieShoppingDatabase.close()
    }


    @Test
    fun insertSearchMovieItem() = runBlockingTest {
        val searchMovieItem = SearchMovieItem("title", "poster_path", 1f, 1, 1)
        searchMovieListDao.insertSearchMovieItem(searchMovieItem)

        val getAllSearchMovieItem = searchMovieListDao.getAllSearchMovieItem().getOrAwaitValue()

        assertThat(getAllSearchMovieItem).contains(searchMovieItem)
    }

    @Test
    fun deleteSearchMovieItem() = runBlockingTest {
        val searchMovieItem = SearchMovieItem("title", "poster_path", 1f, 1)

        searchMovieListDao.insertSearchMovieItem(searchMovieItem)

        searchMovieListDao.deleteSearchMovieItem(searchMovieItem)

        val getAllSearchMovieItem = searchMovieListDao.getAllSearchMovieItem().getOrAwaitValue()

        assertThat(getAllSearchMovieItem).doesNotContain(searchMovieItem)
    }


    @Test
    fun getTotalPrice() = runBlockingTest {
        val searchMovieItem1 = SearchMovieItem("title", "poster_path", 2f, 2, id = 1)
        val searchMovieItem2 = SearchMovieItem("title", "poster_path", 3f, 3, id = 2)
        val searchMovieItem3 = SearchMovieItem("title", "poster_path", 4f, 4, id = 3)
        val searchMovieItem4 = SearchMovieItem("title", "poster_path", 5f, 5, id = 4)

        searchMovieListDao.insertSearchMovieItem(searchMovieItem1)
        searchMovieListDao.insertSearchMovieItem(searchMovieItem2)
        searchMovieListDao.insertSearchMovieItem(searchMovieItem3)
        searchMovieListDao.insertSearchMovieItem(searchMovieItem4)

        val getTotalPrice = searchMovieListDao.getTotalPrice().getOrAwaitValue()

        assertThat(getTotalPrice).isEqualTo(2f * 2 + 3f * 3 + 4f * 4 + 5f * 5)

    }

}